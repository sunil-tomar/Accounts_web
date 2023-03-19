/*
package com.homemaker.Accounts.helper;

import com.sarvatra.common.core.enums.EhubApplicationCache;
import com.sarvatra.common.core.model.*;
import com.sarvatra.service.EntityExtraPermissionService;
import com.sarvatra.service.EntityFeatureService;
import com.sarvatra.service.EntityProductRoleFeatureService;
import com.sarvatra.service.FeaturePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(EntityHelper.class);

    @Autowired
    private EntityProductRoleFeatureService entityProductRoleFeatureService;

    @Autowired
    private EntityFeatureService entityFeatureService;

    @Autowired
    private FeaturePermissionService featurePermissionService;

    @Autowired
    private EntityExtraPermissionService entityExtraPermissionService;

    public Set<Permission> getEntityPermissions(Entity entity) {
        return getEntityPermissionDtos(entity)
                .stream()
                .filter(PermissionDto::isPermissionEnabled)
                .map(permissionDto -> {
                    Permission permission = new Permission();
                    permission.setId(permissionDto.getPermissionId());
                    permission.setValue(permissionDto.getPermissionValue());
                    permission.setProductId(permissionDto.getProductId());
                    return permission;
                }).collect(Collectors.toSet());
    }


    public Set<PermissionDto> getEntityPermissionDtos(Entity entity) {
        Long entityId = entity.getId();
        LOGGER.debug("Fetch Entity Permission for entity {}: START", entityId);
        List<EntityFeature> entityFeatures = entityFeatureService.fetchFeaturesByEntity(entityId);
        Set<PermissionDto> allPermissions = entityFeatures.stream()
                .filter(entityFeature -> Objects.nonNull(entityFeature) && STATUS.ACTIVE.getValue().equalsIgnoreCase(entityFeature.getStatus()))
                .map(entityFeature -> featurePermissionService.fetchByProductRoleFeatureId(entityFeature.getEntityProductRoleFeatureId())
                        .stream()
                        .map(eachFeatPerm -> {
                            EntityProductRoleFeature entityProductRoleFeature = entityProductRoleFeatureService.fetchFeatureById(eachFeatPerm.getEntityProductRoleFeatureId());
                            PermissionDto dto = new PermissionDto(eachFeatPerm.getPermission(), entityProductRoleFeature);
                            dto.setPermissionEnabled(STATUS.ACTIVE.getValue().equals(entityFeature.getStatus()));
                            return dto;
                        }).collect(Collectors.toSet())).flatMap(Set::stream).collect(Collectors.toSet());
        List<EntityExtraPermission> blackListPermissions = entityExtraPermissionService.findByEntityId(entityId);
        allPermissions
                .forEach(dto -> blackListPermissions.stream()
                        .filter(extraPermission -> dto.getPermissionId().equalsIgnoreCase(extraPermission.getPermission()))
                        .findFirst()
                        .ifPresent(entityExtraPermission -> {
                            dto.setPermissionEnabled(false);
                            LOGGER.debug("Found blacklist permission {}", entityExtraPermission.getPermission());
                        }));
        allPermissions.addAll(getParentPermissions(entity));
        LOGGER.debug("Fetch Entity Permission for entity {}: END", entityId);
        return allPermissions;
    }

    public Set<PermissionDto> getParentPermissions(Entity entity) {
        Set<EntityProduct> entityProducts = entity.getEntityProducts();
        Set<PermissionDto> finalInheritedPerms = new HashSet<>();
        entityProducts.stream()
                .filter(EntityProduct::isActive)
                .forEach(ep -> {
                    List<EntityFeature> parentEntityFeatures;
                    if (Objects.isNull(ep.getProductBroughtBy())) {
                        LOGGER.debug("Fetch parentEntityFeatures when parent is null , entity-id :{} and acq-entity-id : {}", entity.getId(), ep.getAcquiringEntity().getId());
                        parentEntityFeatures = entityFeatureService.fetchFeaturesByEntity(ep.getAcquiringEntity().getId());
                    } else {
                        LOGGER.debug("Fetch parentEntityFeatures when parent is not null entity-id : {} and product-brought-by-entity-id : {}", entity.getId(), ep.getProductBroughtBy().getEntityId());
                        parentEntityFeatures = entityFeatureService.fetchFeaturesByEntity(ep.getProductBroughtBy().getEntityId());
                    }
                    finalInheritedPerms.addAll(getParentInheritablePermissions(parentEntityFeatures, ep));
                });
        return finalInheritedPerms;
    }

    private Set<PermissionDto> getParentInheritablePermissions(List<EntityFeature> parentEntityFeatures, EntityProduct entityProduct) {
        LOGGER.debug("Fetch InheritableParentPermissions : START");
        Set<PermissionDto> allPermissions = parentEntityFeatures
                .stream()
                .filter(parentEntityFeature -> Objects.nonNull(parentEntityFeature) && com.sarvatra.common.core.enums.STATUS.ACTIVE.getValue().equalsIgnoreCase(parentEntityFeature.getStatus()))
                .map(parentEntityFeature -> {
                    LOGGER.debug("Fetch permissions for prod-role-feat-id {}", parentEntityFeature.getEntityProductRoleFeatureId());
                    return featurePermissionService.fetchByProductRoleFeatureId(parentEntityFeature.getEntityProductRoleFeatureId())
                            .stream()
                            .map(eachFeatPerm -> {
                                EntityProductRoleFeature productRoleFeature = entityProductRoleFeatureService.fetchFeatureById(eachFeatPerm.getEntityProductRoleFeatureId());
                                PermissionDto dto = null;
                                if (productRoleFeature.isInheritable() && entityProduct.getProduct().getId().equals(productRoleFeature.getProductId())) {
                                    dto = new PermissionDto(eachFeatPerm.getPermission(), productRoleFeature);
                                    LOGGER.debug("Found inheritable permission {} for product-id : {}" , eachFeatPerm.getPermission().getId(), productRoleFeature.getProductId());
                                }
                                return dto;
                            }).collect(Collectors.toSet());
                }).flatMap(Set::stream).collect(Collectors.toSet());
        LOGGER.debug("Fetch InheritableParentPermissions : END");
        return allPermissions;
    }


    public static Set<Product> getAssignedActiveEntityProducts(Entity entity) {
        return entity.getEntityProducts()
                .stream()
                .filter(EntityProduct::isActive)
                .map(EntityProduct::getProduct)
                .collect(Collectors.toSet());
    }

    public static Set<Product> getAssignedActiveAcquiringEntityProducts(Entity entity) {
        return entity.getAcquirerProducts()
                .stream()
                .filter(AcquiringBankProducts::isActive)
                .map(AcquiringBankProducts::getProduct)
                .collect(Collectors.toSet());
    }

    public static Long getSarvatraReferenceId(Entity entity, String productName) {
        if (entity != null) {
            for (AcquiringBankProducts acquiringBankProducts : entity.getAcquirerProducts()) {
                if (StringUtils.isBlank(productName) || acquiringBankProducts.getProduct().getName().equalsIgnoreCase(productName)) {
                    return acquiringBankProducts.getProductSwitchRefId();
                }
            }
            for (EntityProduct entityProduct : entity.getEntityProducts()) {
                if (StringUtils.isBlank(productName) || entityProduct.getProduct().getName().equalsIgnoreCase(productName)) {
                    return getSarvatraReferenceId(entityProduct.getAcquiringEntity(), productName);
                }
            }
        }
        //default Sarvatra ReferenceId
        return Long.parseLong(EhubApplicationCache.INSTANCE.getSrvtReferenceId());
    }


}
*/
