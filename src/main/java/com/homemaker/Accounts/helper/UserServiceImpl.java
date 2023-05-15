/*
package com.homemaker.monthlyexpenses.helper;


import com.sarvatra.common.core.config.yaml.UserAEPSConfig;
import com.sarvatra.common.core.config.yaml.UserBBPSConfig;
import com.sarvatra.common.core.enums.EHUB_PRODUCTS;
import com.sarvatra.common.core.enums.EHUB_PRODUCT_ROLES;
import com.sarvatra.common.core.model.*;
import com.sarvatra.common.core.repository.UserSessionRepository;
import com.sarvatra.dto.ProductAccessDto;
import com.sarvatra.helper.EhubException;
import com.sarvatra.repository.CustomUserRepository;
import com.sarvatra.service.*;
import com.sarvatra.util.AppUtil;
import com.sarvatra.util.EhubUserCache;
import com.sarvatra.util.EntityHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.BiPredicate;

@Service
public class UserServiceImpl implements UserService {


    private static final String ENTITY = "entity";
    private static final String LOG_USER = "User ";
    private static final Set<String> BBPS_TXN_PERMISSIONS = new HashSet<>(Arrays.asList("view-wallet-statement", "manage-complaint", "manage-billing"));
    private static final Set<String> MINI_ATM_TXN_PERMISSIONS = new HashSet<>(Arrays.asList("manage-settlement-report", "view-settlement-report", "view-srvtmnatm-transaction", "view-srvtmnatm-dashboard"));
    private static final Set<String> MICRO_ATM_TXN_PERMISSIONS = new HashSet<>(Arrays.asList("manage-microatm-terminal", "view-microatm-transaction", "view-microatm-recon-data"));
    private static final Set<String> SRVT_AEPS_TXN_PERMISSIONS = new HashSet<>(Arrays.asList("manage-saeps-settlement-report", "view-saeps-settlement-report", "view-saeps-transaction", "view-saeps-dashboard"));
    private static final Set<String> AADHAR_AEPS_ATM_TXN_PERMISSIONS = new HashSet<>(Arrays.asList("manage-aaeps-terminal", "view-aaeps-transaction", "view-aaeps-recon-data"));


    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private EntityHelper entityHelper;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityLogoService entityLogoService;

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public User findById(Long id) {
        return customUserRepository.findById(id).orElse(null);
    }

    @Override
    public User verify(String nick, String password, boolean ignoreCase) throws EhubException {
        return this.verify(nick, password, null, ignoreCase);
    }

    @Override
    public User populateLoginDetails(Long userId) throws EhubException {

        Optional<User> optionalUser = customUserRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new EhubException(HttpStatus.BAD_REQUEST, "Invalid UserId");
        }

        User user = optionalUser.get();

        userPermissionService.addUserPermissions(user);

        if (!user.isActive() || user.isDeleted()) {
            LOGGER.error(LOG_USER + "{} is either not active or deleted", userId);
            return user;
        }

        if (Objects.isNull(user.getEntity())) {
            LOGGER.error(LOG_USER + "{} is not assigned an Entity", userId);
            return user;
        }

        Entity userEntity = user.getEntity();
        user.setHostEntity(userEntity.isHostEntity());

        user.setPerms(user.isAdmin() ? entityHelper.getEntityPermissions(userEntity) : user.getPermissions());

        //To be done in given sequence
        populateAssignedProducts(user);
        populatePermissionSettings(user);
        populateSwitchReferenceConfigs(user);
        populateLoginMsgArray(user);
        fetchEntityWithLogo(user.getEntity(), user);
        return user;
    }

    private void populateAssignedProducts(User user) {
        Set<Product> assignedProducts = new HashSet<>();
        Entity userEntity = user.getEntity();
        if (!user.isHostEntity()) {
            assignedProducts = EntityHelper.getAssignedActiveEntityProducts(userEntity);
            assignedProducts.addAll(EntityHelper.getAssignedActiveAcquiringEntityProducts(userEntity));
        } else if (user.isHostEntity() && !user.isAdmin()) {
            List<Long> productsForPermissions = new ArrayList<>();
            Set<Product> tempAssignedProds = new HashSet<>();
            user.getPerms()
                    .forEach(permission -> {
                        if (Objects.nonNull(permission.getProductId())) {
                            Long productId = permission.getProductId();
                            if (!productsForPermissions.contains(productId)) {
                                productsForPermissions.add(productId);
                                tempAssignedProds.add(productService.findById(productId).orElse(null));
                            }
                        }
                    });
            assignedProducts.addAll(tempAssignedProds);
        } else {
            assignedProducts.addAll(productService.getProducts());
        }
        user.setAssignedProducts(assignedProducts);
    }

    private void populatePermissionSettings(User user) {
        Set<Permission> permissions = user.getPerms();
        ProductAccessDto productAccessDto = new ProductAccessDto();
        for (Permission permission : permissions) {
            if (permission.getProductId() == 0L) {
                updateDefaultProductFlags(permission, productAccessDto);
            } else {
                updateNonDefaultProductFlags(permission, productAccessDto, user);
            }
            LOGGER.debug("User has permission {}", permission.getId());
        }
        user.setSuperUser(productAccessDto.isSuperUser());
        user.setShowConfig(productAccessDto.isShowConfig());
        user.setShowAEPS(productAccessDto.isShowAEPS());
        user.setShowBBPS(productAccessDto.isShowBBPS());
        user.setShowMiniATM(productAccessDto.isShowMiniATM());
        user.setShowMicroATM(productAccessDto.isShowMicroATM());
        user.setShowFlexiRecon(productAccessDto.isShowFlexiRecon());
        // user.setShowSrvtAeps(showSrvtAeps);
        // user.setShowAadharAEPS(showAadharAEPS);
    }

    private void updateNonDefaultProductFlags(Permission permission, ProductAccessDto productAccessDto, User user) {
        long productId = permission.getProductId();
        if (isBBPSProductShowOnUI(productAccessDto, permission, productId)) {
            productAccessDto.setShowBBPS(true);
        } else if (isAEPSProductShowOnUI(permission, productId)) {
            productAccessDto.setShowAEPS(true);
        } else if (isMiniATMProductShowOnUI(permission, productId, user)) {
            productAccessDto.setShowMiniATM(true);
        } else if (isMicroATMProductShowOnUI(permission, productId)) {
            productAccessDto.setShowMicroATM(true);
        } else if (isFlexiReconProductShowOnUI(productId)) {
            productAccessDto.setShowFlexiRecon(true);
        } else if (isSarvatraAEPSProductShowOnUI(permission, productId, user)) {
            productAccessDto.setShowSrvtAeps(true);
        } else if (isAadhaarAEPSProductShowOnUI(permission, productId)) {
            productAccessDto.setShowAadharAEPS(true);
        }
    }

    private boolean isBBPSProductShowOnUI(ProductAccessDto productAccessDto, Permission permission, Long productId) {
        return !productAccessDto.isShowBBPS() && productId == EHUB_PRODUCTS.BBPS.getProductId() && BBPS_TXN_PERMISSIONS.contains(permission.getId());
    }

    private boolean isAEPSProductShowOnUI(Permission permission, Long productId) {
        return productId == EHUB_PRODUCTS.AEPS.getProductId() && "manage-demo-kyc".equals(permission.getId());
    }

    private boolean isMiniATMProductShowOnUI(Permission permission, Long productId, User user) {
        return productId == EHUB_PRODUCTS.SRVT_MN_ATM.getProductId() && (MINI_ATM_TXN_PERMISSIONS.contains(permission.getId()) || (user.isHostEntity() && "manage-terminal".equals(permission.getId())));
    }

    private boolean isMicroATMProductShowOnUI(Permission permission, Long productId) {
        return productId == EHUB_PRODUCTS.MICRO_ATM.getProductId() && MICRO_ATM_TXN_PERMISSIONS.contains(permission.getId());
    }

    private boolean isFlexiReconProductShowOnUI(Long productId) {
        return productId == EHUB_PRODUCTS.FLEXI_RECON.getProductId();
    }

    private boolean isSarvatraAEPSProductShowOnUI(Permission permission, Long productId, User user) {
        Product product = productService.findByName("SRVT_AEPS").orElse(null);
        if (Objects.isNull(product)) {
            return false;
        }
        return productId.equals(product.getId()) && (SRVT_AEPS_TXN_PERMISSIONS.contains(permission.getId()) || (user.isHostEntity() && "manage-saeps-terminal".equals(permission.getId())));
    }

    private boolean isAadhaarAEPSProductShowOnUI(Permission permission, Long productId) {
        Product product = productService.findByName("AADHAR_AEPS").orElse(null);
        if (Objects.isNull(product)) {
            return false;
        }
        return productId.equals(product.getId()) && AADHAR_AEPS_ATM_TXN_PERMISSIONS.contains(permission.getId());
    }


    private void updateDefaultProductFlags(Permission permission, ProductAccessDto productAccessDto) {
        if ("super-user".equals(permission.getId())) {
            productAccessDto.setSuperUser(true);
            productAccessDto.setShowConfig(true);
            productAccessDto.setShowAEPS(true);
            productAccessDto.setShowBBPS(true);
            productAccessDto.setShowMiniATM(true);
            productAccessDto.setShowMiniATM(true);
            productAccessDto.setShowFlexiRecon(true);
            productAccessDto.setShowSrvtAeps(true);
            productAccessDto.setShowAadharAEPS(true);
        } else if (!"manage-report".equals(permission.getId())) {
            productAccessDto.setShowConfig(true);
        }
    }

    private boolean productAssigned(Set<Product> assignedProducts, Product product) {
        return assignedProducts.stream()
                .anyMatch(assignedProduct -> assignedProduct.getName().equalsIgnoreCase(product.getName()));
    }

    private void populateSwitchReferenceConfigs(User user) {

        Set<Product> assignedProducts = user.getAssignedProducts();
        Set<EntityProduct> entityProducts = user.getEntity().getEntityProducts();

        if (CollectionUtils.isEmpty(entityProducts)) {
            return;
        }

        BiPredicate<EntityProduct, Set<Product>> skipCheck = (entityProduct, userAssignedProducts) -> Objects.isNull(entityProduct.getProduct())
                || !entityProduct.isActive()
                || !productAssigned(userAssignedProducts, entityProduct.getProduct())
                || Objects.isNull(entityProduct.getProductRole())
                || Objects.isNull(entityProduct.getProductSwitchRefId());

        entityProducts.stream()
                .filter(entityProduct -> !skipCheck.test(entityProduct, assignedProducts))
                .forEach(entityProduct -> {
                    if (entityProduct.getProduct().getName().equalsIgnoreCase(EHUB_PRODUCTS.BBPS.name())) {
                        if (entityProduct.getProductRole().getName().equalsIgnoreCase(EHUB_PRODUCT_ROLES.AGENT.getValue())) {
                            LOGGER.info(LOG_USER + "{} is an Agent", user.getName());
                            UserBBPSConfig userBBPSConfig = new UserBBPSConfig();
                            userBBPSConfig.setProductRole(EHUB_PRODUCT_ROLES.getName(entityProduct.getProductRole().getName()));
                            userBBPSConfig.setAgentId(String.valueOf(entityProduct.getProductSwitchRefId()));
                            userBBPSConfig.setOuId(EntityHelper.getSarvatraReferenceId(entityProduct.getAcquiringEntity(), EHUB_PRODUCTS.BBPS.name()));
                            user.setUserBBPSConfig(userBBPSConfig);
                        } else if (entityProduct.getProductRole().getName().equalsIgnoreCase(EHUB_PRODUCT_ROLES.AGENT_GROUP.getValue())) {
                            LOGGER.info(LOG_USER + "{} is an Agent Group", user.getName());
                            UserBBPSConfig userBBPSConfig = new UserBBPSConfig();
                            userBBPSConfig.setProductRole(EHUB_PRODUCT_ROLES.getName(entityProduct.getProductRole().getName()));
                            userBBPSConfig.setAgentGroupId(entityProduct.getProductSwitchRefId());
                            user.setUserBBPSConfig(userBBPSConfig);
                        }
                    } else if (entityProduct.getProduct().getName().equalsIgnoreCase(EHUB_PRODUCTS.AEPS.name()) && entityProduct.getProductRole().getName().equalsIgnoreCase(EHUB_PRODUCT_ROLES.BC_AGENT.getValue())) {
                        LOGGER.info(LOG_USER + "{} is a BC Agent", user.getName());
                        UserAEPSConfig userAEPSConfig = new UserAEPSConfig();
                        userAEPSConfig.setProductRole(EHUB_PRODUCT_ROLES.getName(entityProduct.getProductRole().getName()));
                        userAEPSConfig.setProductSwitchRefId(entityProduct.getProductSwitchRefId());
                        userAEPSConfig.setInstitutionId(EntityHelper.getSarvatraReferenceId(entityProduct.getAcquiringEntity(), EHUB_PRODUCTS.AEPS.name()));
                        user.setUserAEPSConfig(userAEPSConfig);
                    }
                });
    }

    private void populateLoginMsgArray(User user) {
        Entity userEntity = user.getEntity();
        //Host Entity should not have any message.
        if (userEntity.isHostEntity()) {
            return;
        }
        Set<String> msgs = new HashSet<>();
        Set<AcquiringBankProducts> acquirerProducts = userEntity.getAcquirerProducts();
        Set<EntityProduct> entityProducts = userEntity.getEntityProducts();
        if (CollectionUtils.isEmpty(acquirerProducts) && CollectionUtils.isEmpty(entityProducts)) {
            msgs.add("No Product has been assigned to your Entity.");
            user.setLoginStatusMsgs(msgs);
            return;
        }
        Set<Product> assignedProductSet = user.getAssignedProducts();
        Set<Product> unAssignProductSet = new HashSet<>();
        updateAssignedAndUnassignedProducts(msgs, acquirerProducts, entityProducts, assignedProductSet, unAssignProductSet);
        if (!user.isAdmin()) {
            updateNonAdminMsgs(user, msgs, assignedProductSet);
        }
        user.setLoginStatusMsgs(msgs);
    }

    private void updateNonAdminMsgs(User user, Set<String> messages, Set<Product> assignedProductSet) {
        Set<Permission> permissionSet = user.getPermissions();
        if (user.getPermissions().isEmpty()) {
            messages.add("No Permissions have been assigned to you.");
        } else {
            Set<Long> activeProductWithPerm = new HashSet<>();
            permissionSet.stream()
                    .filter(permission -> Objects.nonNull(permission.getProductId()))
                    .forEach(permission -> activeProductWithPerm.add(permission.getProductId()));
            if (assignedProductSet.size() > activeProductWithPerm.size()) {
                for (Product notAssignedProd : assignedProductSet) {
                    if (!activeProductWithPerm.contains(notAssignedProd.getId())) {
                        messages.add("No Permission has been assigned to you for the active Product '" + notAssignedProd.getDisplayName() + "'.");
                    }
                }
            }
        }
    }

    private void updateAssignedAndUnassignedProducts(Set<String> msgs, Set<AcquiringBankProducts> acquirerProducts, Set<EntityProduct> entityProducts, Set<Product> assignedProductSet, Set<Product> unAssignProductSet) {

        for (AcquiringBankProducts bp : acquirerProducts) {
            if (!bp.isActive() && !assignedProductSet.contains(bp.getProduct())) {
                unAssignProductSet.add(bp.getProduct());
            }
        }

        for (EntityProduct ep : entityProducts) {
            if (!ep.isActive() && !assignedProductSet.contains(ep.getProduct()) && !unAssignProductSet.contains(ep.getProduct())) {
                unAssignProductSet.add(ep.getProduct());
                msgs.add("Product '" + ep.getProduct().getDisplayName() + "' is no more active for your Entity.");
            }
        }
    }

    private void fetchEntityWithLogo(Entity thisEntity, User user) {
        if (thisEntity == null) {
            return;
        }
        if (thisEntity.isLogoUploaded()) {
            user.setLogoId(thisEntity.getId() + "");
            user.setLogoExt(entityLogoService.findByEntityId(thisEntity.getId()).getLogoExtension());
        } else if (Objects.nonNull(thisEntity.getBroughtBy()) && !thisEntity.isHostEntity()) {
            fetchEntityWithLogo(thisEntity.getBroughtBy(), user);
        }
    }

    private static Specification<User> findUserByNickSpecification(String nick, boolean includeDeleted, Entity entity, boolean ignoreCase, boolean excludeNotActive) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ignoreCase) {
                predicates.add(criteriaBuilder.equal(root.get("nick"), nick));
            } else {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nick")),
                        nick.toLowerCase()));
            }
            if (entity != null) {
                predicates.add(criteriaBuilder.equal(root.get(ENTITY), entity));
            }
            if (!includeDeleted) {
                predicates.add(criteriaBuilder.equal(root.get("deleted"), Boolean.FALSE));
            }
            if (excludeNotActive) {
                predicates.add(criteriaBuilder.equal(root.get("active"), Boolean.TRUE));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    @Override
    public User verify(String nick, String password, Entity entity, boolean ignoreCase) throws EhubException {

        Optional<User> optionalUser = customUserRepository.findAll(findUserByNickSpecification(nick, false, entity, ignoreCase, true)).stream().findFirst();

        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            if (u.getLoginAttempts() == 3) {
                throw new EhubException(HttpStatus.BAD_REQUEST, "User is locked. Please get in touch with the Administrator");
            }
            if (checkPassword(u, password)) {
                u.setLastSuccessfulLogin(u.getCurrentLogin());
                u.setCurrentLogin(new Date());
                unlockUser(u);
                return u;
            } else {
                incrementFailedAttempt(u);
                if (u.getLoginAttempts() == 3) {
                    throw new EhubException(HttpStatus.BAD_REQUEST, "Invalid Username or Password. " +
                            "Account is locked as max failed attempts reached. Please contact the Administrator");
                }
                String failedAttempts = (u.getLoginAttempts() == 1) ? "first attempt" : "second attempt";
                throw new EhubException(HttpStatus.BAD_REQUEST, "Invalid Username or Password. " +
                        "This is your " + failedAttempts +
                        ". Maximum three failed attempts are allowed before account is locked.");
            }
        }
        return null;
    }

    @Override
    public void unlockUser(User user) {
        user.setLoginAttempts(0);
        customUserRepository.save(user);
    }


    private void incrementFailedAttempt(User user) {
        user.setLoginAttempts(user.getLoginAttempts() + 1);
        customUserRepository.save(user);
    }

    @Override
    public boolean checkPassword(User u, String clearPasswordString) {
        String password = u.getPassword();
        long authType = u.getAuthType();
        if (authType == 2) {
            return password.equals(AppUtil.getLegacyHashing(clearPasswordString));
        } else {
            return password.equals(AppUtil.getHash(u.getId(), clearPasswordString));
        }
    }

    private String getUserCookie(HttpServletRequest httpServletRequest) {
        String cookie = null;
        if (httpServletRequest.getCookies() != null) {
            Cookie[] var2 = httpServletRequest.getCookies();
            for (Cookie c : var2) {
                if (StringUtils.equals("ck", c.getName())) {
                    cookie = c.getValue();
                    break;
                }
            }
        }
        return cookie;
    }

    @Override
    public final User getSessionUserForWebPortal(HttpServletRequest httpServletRequest) {
        UserSession sessionUser;
        try {
            String cookie = getUserCookie(httpServletRequest);
            sessionUser = getSessionUserByCookie(cookie);
            return fetchSessionUser(sessionUser);
        } catch (Exception ex) {
            LOGGER.error("Error in getSessionUserForWebPortal and msg is {}", ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }


    private UserSession getSessionUserByCookie(String cookie) {
        if (StringUtils.isNotBlank(cookie)) {
            return userSessionService.getSessionUserByCookie(cookie);
        } else {
            LOGGER.warn("!! WARN : httpRequest.getCookies() is not set, first-time login or via non-secure port. ");
        }
        return null;
    }

    private User fetchSessionUser(UserSession userSession) throws EhubException {
        if (userSession != null) {
            long interval = new Date().getTime() - userSession.getUpdated().getTime();
            if (interval > (15 * 60 * 1000)) {
                throw new AccessDeniedException("Forbidden access");
            }
            String cacheKey = userSession.getKey() != null ? userSession.getKey() : userSession.getAccessToken();
            userSession.setUpdated(new Date());
            userSessionService.save(userSession);
            return getFromCache(userSession, cacheKey);
        }
        return null;
    }

    @Override
    public User getFromCache(UserSession userSession, String cacheKey) throws EhubException {
        User user = EhubUserCache.INSTANCE.get(cacheKey);
        if (user == null) {
            user = this.populateLoginDetails(userSession.getUser());
            if (user != null) {
                EhubUserCache.INSTANCE.put(cacheKey, user);
            }
        }
        return user;
    }

}
*/
