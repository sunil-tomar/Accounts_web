package com.homemaker.Accounts.utils;


public abstract class RegularExpressions {

    private RegularExpressions() {
        // left it empty intentionally
    }

    public static final String GST_NUMBER_KEY = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}\\d[Z]{1}[A-Z\\d]{1}$";
    public static final String GST_NUMBER_MSG = "Not a valid GST Number format";

    public static final String EMAIL_KEY = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*\\.(.[A-Za-z]{1,})$";
    public static final String EMAIL_MSG = "Invalid Email Address -> Valid emails:user@gmail.com or my.user@domain.com etc.";

    public static final String TIME_24_HOURS_KEY = "^([01]\\d|2[0-3]):?([0-5]\\d)$";
    public static final String TIME_24_HOURS_MSG = "Invalid Time in 24 hours format. Valid ex: 15:30";

    public static final String VALUES_500_TO_10000_KEY = "^(5\\d{2}|[6-9]\\d{2}|\\d{4}|10000)$";
    public static final String VALUES_500_TO_10000_MSG = "Valid value range is between 500 to 10000";

    public static final String VALUES_5000_TO_500000_KEY = "^(5\\d{3}|[6-9]\\d{3}|\\d{5}|[1-4]\\d{5}|500000)$";
    public static final String VALUES_5000_TO_500000_MSG = "Valid value range is between 5000 to 500000";

    public static final String VALUES_0_TO_250_KEY = "^([\\d]|\\d{2}|1\\d{2}|2[0-4][0-9]|250)$";
    public static final String VALUES_0_TO_250_MSG = "Valid value range is between 0 to 250";

    public static final String MULTIPLE_EMAIL_KEY = "^(\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]{2,4}\\s*?;?\\s*?)+$";
    public static final String MULTIPLE_EMAIL_MSG = "Invalid Email Address-> Valid emails:user@gmail.com or my.user@domain.com etc";

    public static final String CITY_KEY = "^[a-zA-Z]+(?:[\\s-&,.]+[a-zA-Z]+)*$";
    public static final String CITY_MSG = "Only alphabets, whitespace, hyphen, comma, dot and & allowed for City";

    public static final String ADDRESS_KEY = RegularExpressions.ALPHANUMERIC_FEW_CHARS_KEY;
    public static final String ADDRESS_MSG = RegularExpressions.ALPHANUMERIC_FEW_CHARS_MSG;

    public static final String SYS_CONFIG_KEY_KEY = "^(?=.*[a-zA-Z0-9])([a-zA-Z\\d\\_.]+)$";
    public static final String SYS_CONFIG_KEY_MSG = "Only alphanumeric (min one), underscore and dot allowed for Id";

    public static final String IFSC_CODE_KEY = "^[A-Z]{4}[A-Z0-9]{7}$";
    public static final String IFSC_CODE_MSG = "Not a valid IFSC code format";

    public static final String ACCOUNT_NUMBER_KEY = "^([0-9A-Za-z]{9,20})$";
    public static final String ACCOUNT_NUMBER_MSG = "Not a valid Account number";

    public static final String PIN_KEY = "^[1-9][\\d]{5}$";
    public static final String PIN_MSG = "Not a valid Pin";

    public static final String SUBSCRIPTION_AMOUNT_KEY = "^([\\d]{1,6})$";
    public static final String SUBSCRIPTION_AMOUNT_MSG = "Not a valid Subscription Amount";

    public static final String MOBILE_KEY = "^([6-9][0-9]{9})$";
    public static final String MOBILE_MSG = "Mobile number should be 10 digits, starting with 6, 7, 8 or 9.";

    public static final String TELEPHONE_NO_KEY = "^([0-9]{8,20})$";
    public static final String TELEPHONE_NO_MSG = "Telephone number should be between 8 and 20 digits";

    public static final String MCC_CODE_KEY = "^([\\d]{4})$";
    public static final String MCC_CODE_MSG = "Not a valid MCC Code";

    public static final String TXN_COUNT_CALC_BASE_KEY = "^([T|C])$";
    public static final String TXN_COUNT_CALC_BASE_MSG = "Not a valid Transaction Count Calculation Base";

    public static final String ALPHANUMERIC_MIN_ONE_ALPHA_KEY = "^(?=.*[a-zA-Z])([a-zA-Z\\d\\s]+)$";
    public static final String ALPHANUMERIC_MIN_ONE_ALPHA_MSG = "Only alphanumeric with minimum one alphabet allowed";

    public static final String ALPHANUMERIC_FEW_CHARS_KEY = "^(?=.*[a-zA-Z])([\\W\\w]+)$";
    public static final String ALPHANUMERIC_FEW_CHARS_MSG = "Only alphanumeric (starting with alphabet) and special characters allowed";

    public static final String ALPHABETS_KEY = "^[A-Za-z]+$";
    public static final String ALPHABETS_MSG = "Only alphabets allowed";

    public static final String ALPHABETS_FEW_SPL_CHARS_KEY = "^(?=.*[a-zA-Z])([a-zA-Z-.\\s()]+)$";
    public static final String ALPHABETS_FEW_SPL_CHARS_MSG = "Only alphabets, hyphen, dot, space and small brackets are allowed";

    public static final String PAN_KEY = "^[A-Za-z]{5}\\d{4}[A-Za-z]{1}$";
    public static final String PAN_MSG = "Not a valid Pan";

    public static final String AADHAAR_NUMBER_KEY = "^(\\d{12}|\\d{16})$";
    public static final String AADHAAR_NUMBER_MSG = "Aadhaar Number can be either 12 digits or 16 digits long";

    public static final String POS_SRNO_WITH_LENGTH_KEY = "^[A-Za-z0-9]{10,30}$";
    public static final String POS_SRNO_WITH_LENGTH_MSG = "Not a valid POS Serial Number";

    public static final String TERMINAL_ID_WITH_LENGTH_KEY = "^M[A-Z]S[0-9]{5}$";
    public static final String TERMINAL_ID_WITH_LENGTH_MSG = "Not a valid Terminal Id";

    public static final String SIM_CARD_TYPE_KEY = "^([a-zA-Z]{1,20})$";
    public static final String SMUI_TERMINAL_TYPE_ID_KEY = "^[0-9]{1,}$";
    public static final String SMUI_INCENTIVE_RATE_KEY = "^[0-9.]{1,}$";
    public static final String ADDRESS_WITH_LENGTH_KEY = "^(?=.*[a-zA-Z])(([\\W\\w]){4,40})$";
    public static final String LOCATION_KEY = "^(?=.*[a-zA-Z])([a-zA-Z0-9-_\\s]{4,40})$";
    public static final String CITY_WITH_LENGTH_KEY = "^(?=.*[a-zA-Z])([a-zA-Z-\\s&,.]{3,13})$";
    public static final String STATE_WITH_LENGTH_KEY = "^[a-zA-Z]{2}$";
    public static final String TMK_WITH_LENGTH_KEY = "^U[A-Z0-9]{32}$";
    public static final String PROGRAM_ID_WITH_LENGTH_KEY = "^([0-9]{1,20})$";
    public static final String UID_KEY = "^([0-9]{12})$";

    public static final String DOB_KEY = "^\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$";
    public static final String UID_TYPE_KEY = "^[U|V|T]$";
    public static final String GENDER_KEY = "^[M|F|T]$";
    public static final String UID_VID_TOKENID_KEY = "^[\\w\\W]{12,72}$";

    public static final String ALPHABETS_ONLY_SPACES_KEY = "^(?=.*[a-zA-Z])([a-zA-Z\\s]+)$";
    public static final String ALPHABETS_ONLY_SPACES_MSG = "Only alphabets and spaces are allowed";


    //For MicroAtm bulk upload
    //BC
    public static final String SRNO_WITH_LENGTH_KEY = "^[0-9]*$";
    public static final String SRNO_WITH_LENGTH_MSG = "Not a valid BC Serial Number";

    public static final String BC_BCID_WITH_LENGTH_KEY = "^[0-9]*$";
    public static final String BC_BCID_WITH_LENGTH_MSG = "a valid BCID Serial Number";

    public static final String BC_NAME_WITH_LENGTH_KEY = "^[0-9a-zA-Z _-]{4,100}$";
    public static final String BC_NAME_WITH_LENGTH_MSG = "valid BCName 4-100 alpha-numeric underscore,dash and space is allowed";

    public static final String BC_SHORT_CODE_WITH_LENGTH_KEY = "^[A-Z]{2}$";
    public static final String BC_SHORT_CODE_WITH_LENGTH_MSG = "Valid BC Short Code ONLY 2 Char Allow ";

    //Merchant
    public static final String M_NAME_WITH_LENGTH_KEY = "^[0-9a-zA-Z _-]{4,100}$";
    public static final String M_NAME_WITH_LENGTH_MSG = "valid Merchant Name 4-100 alpha-numeric underscore,dash and space is allowed";

    //Terminal
    public static final String T_POS_SRNO_WITH_LENGTH_KEY = "^[A-Za-z0-9]{10,30}$";
    public static final String T_POS_SRNO_WITH_LENGTH_MSG = "Not a valid POS Serial Number";


    // MicroatmCommon
    public static final String MICROATM_GST_NUMBER_KEY = RegularExpressions.GST_NUMBER_KEY;
    public static final String MICROATM_GST_NUMBER_MSG = RegularExpressions.GST_NUMBER_MSG;

    public static final String MICROATM_MOBILE_KEY = RegularExpressions.MOBILE_KEY;
    public static final String MICROATM_MOBILE_MSG = RegularExpressions.MOBILE_MSG;

    public static final String MICROATM_TELEPHONE_NO_KEY = RegularExpressions.TELEPHONE_NO_KEY;
    public static final String MICROATM_TELEPHONE_NO_MSG = RegularExpressions.TELEPHONE_NO_MSG;

    public static final String MICROATM_EMAIL_KEY = RegularExpressions.EMAIL_KEY;
    public static final String MICROATM_EMAIL_MSG = RegularExpressions.EMAIL_MSG;

    public static final String MICROATM_ADDRESS_WITH_LENGTH_KEY = RegularExpressions.ADDRESS_WITH_LENGTH_KEY;
    public static final String MICROATM_ADDRESS_WITH_LENGTH_MSG = RegularExpressions.ADDRESS_MSG;

    public static final String MICROATM_CITY_WITH_LENGTH_KEY = "^(?=.*[a-zA-Z])([a-zA-Z-\\s&,.]{3,25})$";
    public static final String MICROATM_CITY_WITH_LENGTH_MSG = RegularExpressions.CITY_MSG;

    public static final String MICROATM_STATE_WITH_LENGTH_KEY = RegularExpressions.STATE_WITH_LENGTH_KEY;

    public static final String MICROATM_PIN_CODE_KEY = RegularExpressions.PIN_KEY;
    public static final String MICROATM_PIN_CODE_MSG = RegularExpressions.PIN_MSG;

    public static final String MICROATM_MID_KEY = "^[A-Za-z0-9-_.]{15}$";
    public static final String MID_MSG = "alpha numeric 15 digit length";

    public static final String MICROATM_MIGRATED_TO_SARVATRA_KEY = "^[U]$";
    public static final String PRODUCT_PERMISSION = "^[Y,N]$";



    //PPI PROGRAM MANAGER
    public static final String PPI_PM_PROGRAM_MANAGER_NAME_ALPHANUMERIC_KEY="^([a-zA-Z0-9]+\\s)*[a-zA-Z0-9]+$";
    public static final String PPI_PM_PROGRAM_MANAGER_NAME_ALPHANUMERIC_MESSAGE="Program manager name must be alphanumeric & multiple spaces are not allowed.";

    public static final String PPI_PM_PROGRAM_MANAGER_ID_KEY="^[0-9]{4}$";
    public static final String PPI_PM_PROGRAM_MANAGER_ID_MSG="Program Manager ID must be Numeric";

    public static final String PPI_PM_PROCESSOR_ID_KEY="^[0-9]{2}$";
    public static final String PPI_PM_PROCESSOR_ID_MSG="Processor ID must be numeric";

    public static final String PPI_PM_ADDRESS_KEY="^([a-zA-Z0-9-,/]+\\s)*[a-zA-Z0-9]+$";
    //^([a-zA-z0-9]+[ ][a-zA-Z0-9]+)$
    public static final String PPI_PM_ADDRESS_MSG="Address must be alphanumeric (can have special character) and multiple spaces are not allowed.";

    //public static final String PPI_PM_DISTRICT_KEY="^[a-zA-Z]+(?:[\\s-&,.]+[a-zA-Z]+)*$";
    public static final String PPI_PM_DISTRICT_KEY="^([A-Za-z.,&-]+\\s)*[a-zA-Z.,&-]+$";
    public static final String PPI_PM_DISTRICT_MSG="Only alphabets, whitespace, hyphen, comma, dot and & allowed for District";

    public static final String PPI_PM_PIN_CODE_MSG="PIN Code must be Numeric";

    public static final String PPI_PM_TELEPHONE_NO_KEY = "^([0-9]{10,15})$";
    public static final String PPI_PM_TELEPHONE_NO_MSG = "PM Telephone Number must be Numeric";

    public static final String PPI_PM_MOBILE_NUMBER_MSG="PM Mobile Number  must be numeric, starting with 6, 7, 8 or 9";

   // public static final String PPI_PM_EMAIL_ID_MSG="Invalid Email Address";

    public static final String PPI_PM_CURRENT_ACCOUNT_NUMBER_KEY="^[a-zA-Z0-9]*$";
    public static final String PPI_PM_CURRENT_ACCOUNT_NUMBER_MSG="Current A/c Number should be alphanumeric";

    public static final String PPI_PM_CURRENT_ACCOUNT_WITH_KEY ="^[a-zA-Z.]+(?:[\\s]+[a-zA-Z.]+)*$";
    public static final String PPI_PM_CURRENT_ACCOUNT_WITH_MSG="Current A/c with should be alphabet"+"\n"+"Current A/c with must be between 3 and 50 Chars long and special character as . allowed";

    public static final String PPI_PM_SHORT_CODE_KEY="^[a-zA-Z]{6}$";
    public static final String PPI_PM_SHORT_CODE_MSG="PM Short Code should be alphabets only";

    //public static final String PPI_PM_CONTACT_PERSON_NAME_KEY ="^[a-zA-Z]+(?:[\\s]+[a-zA-Z]+)*$";
    public static final String PPI_PM_CONTACT_PERSON_NAME_KEY ="^([a-zA-Z]+\\s)*[a-zA-Z]+$";
    public static final String PPI_PM_CONTACT_PERSON_NAME_MSG ="Contact Person Name should be alphabets & multiple spaces are not allowed.";

    public static final String PPI_PM_CONTACT_PERSON_MOBILE_MSG="Contact Person Mobile must be numeric, starting with 6, 7, 8 or 9";

    public static final String PPI_PM_PREFIX_KEY="^[A-Z]{4}$";
    public static final String PPI_PM_PREFIX_MSG="Prefix must be alphabets & uppercase";

    //PPI PREPAID AGENT INSTITUTION
    public static final String PPI_PAI_PAI_ID_KEY=RegularExpressions.PPI_PM_PROGRAM_MANAGER_NAME_ALPHANUMERIC_KEY;
    public static final String PPI_PAI_PAI_ID_MSG="Prepaid Agent Institution ID must be alphanumeric";

    //public static final String PPI_PAI_PAI_NAME_KEY=RegularExpressions.PPI_PM_PROGRAM_MANAGER_NAME_ALPHANUMERIC_KEY;
    public static final String PPI_PAI_SOCIETY_NAME_ALPHANUMERIC_KEY="^([a-zA-Z0-9]+\\s)*[a-zA-Z0-9]+$";
    public static final String PPI_PAI_PAI_NAME_KEY=RegularExpressions.PPI_PAI_SOCIETY_NAME_ALPHANUMERIC_KEY;
    public static final String PPI_PAI_PAI_NAME_MSG="Prepaid Agent Institution Name must be alphanumeric & multiple spaces are not allowed.";

    public static final String PPI_PAI_ADDRESS_MSG="Address must be alphanumeric (can have special character) and multiple spaces are not allowed.";

    public static final String PPI_PAI_DISTRICT_KEY=RegularExpressions.PPI_PM_DISTRICT_KEY;
    public static final String PPI_PAI_DISTRICT_MSG="District must be alphabets";

    public static final String PPI_PAI_PIN_CODE_MSG=RegularExpressions.PPI_PM_PIN_CODE_MSG;

    public static final String PPI_PAI_TELEPHONE_NO_KEY=RegularExpressions.PPI_PM_TELEPHONE_NO_KEY;
    public static final String PPI_PAI_TELEPHONE_NO_MSG ="Prepaid Agent Institution Telephone Number must be Numeric";

    public static final String PPI_PAI_MOBILE_NUMBER_MSG="Prepaid Agent Institution Mobile Number must be numeric, starting with 6, 7, 8 or 9";

    //public static final String PPI_PAI_EMAIL_ID_MSG = RegularExpressions.PPI_PM_EMAIL_ID_MSG;

    public static final String PPI_PAI_SHORT_CODE_KEY = "^[A-Z]{6}$";
    public static final String PPI_PAI_SHORT_CODE_MSG = "Short Code must be alphabets and in uppercase";

    public static final String PPI_PAI_CONTACT_PERSON_NAME_KEY=RegularExpressions.PPI_PM_CONTACT_PERSON_NAME_KEY;
    public static final String PPI_PAI_CONTACT_PERSON_NAME_MSG="Contact Person Name Should be alphabets & multiple spaces are not allowed.";

    public static final String PPI_PAI_CONTACT_PERSON_MOBILE_MSG= RegularExpressions.PPI_PM_CONTACT_PERSON_MOBILE_MSG;

    public static final String PPI_PAI_SOCIETY_GST_NO_MSG="Prepaid Agent Institution GST No. must be alphanumeric";

    public static final String PPI_PAI_SOCIETY_PAN_KEY="^[A-Z]{5}\\d{4}[A-Z]{1}$";
    public static final String PPI_PAI_SOCIETY_PAN_MSG="It should be ten characters long.The first five characters should be any upper case alphabets.The next four-characters should be any number from 0 to 9.The last(tenth) character should be any upper case alphabet.It should not contain any white spaces";

    public static final String PPI_PAI_SOCIETY_TAN_KEY="^[A-Z]{4}\\d{5}[A-Z]{1}$";
    public static final String PPI_PAI_SOCIETY_TAN_MSG="The first 4 digits are capital letters of the alphabet. The 5th to the 9th digit is numerical. The 10th digit is again a capital letter";

    public static final String PPI_PAI_IFSC_CODE_MSG="IFSC code must be alphanumeric";


    //PPI CUSTOMER
    public static final String PPI_CUST_MOBILE_NUMBER_MSG="Customer Mobile Number must be numeric, starting with 6, 7, 8 or 9";
    public static final String PPI_CUST_MOTHER_NAME_REGEX="^([a-zA-Z]+\\s)*[a-zA-Z]+$";
    /////////////////////////////////////////

    public static final String PPI_CUSTREG_PAN_REGEX="^[A-Z]{5}\\d{4}[A-Z]{1}$";


    public static final String PPI_CUST_CARD_KIT_ID_NUMERIC_KEY="^([0-9]*$)";

    // Customer address validation constants///
    public static final String PPI_CARD_ADDRESS1_REGEX="^(([a-zA-Z0-9!-\\/:-@[-`{-~]+\\s?)*([a-zA-Z0-9])){3,100}$";
    public static final String PPI_CARD_ADDRESS_PINCODE_REGEX="^[1-9][0-9]{5}$";

    public static final String PPI_EMAIL_REGEX="^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@([^~!@/#$\\s%^&*()_+{}:;''\"\",.<>?-][A-Za-z0-9-]+)[\\.]{1}([A-Za-z0-9-]+)(\\.[A-Za-z]{2,6})?$";

    public static final String PPI_ADDRESS_KEY="^([a-zA-Z0-9~!@/#$%^&*()_+{}:;,.<>?-]+\\s)*[a-zA-Z0-9~!@/#$%^&*()_+{}:;,.<>?-]+$";

    public static final String PPI_WORKFLOW_ID_REGEX = RegularExpressions.PPI_CUST_CARD_KIT_ID_NUMERIC_KEY;
    public static final String PPI_CUST_REG_EKYC_ALPHABETIC_REGEX="^[a-zA-Z]+( [a-zA-Z]+)*$";

    //Monthly-Expense
    public static final String ACC_PAID_FOR_MSG="Account paid for must be alphanumeric & multiple spaces are not allowed.";
    public static final String ACC_PAID_FOR_REG_KEY=RegularExpressions.ACC_PAID_FOR_REG_ALPHANUMERIC_KEY;
    public static final String ACC_PAID_FOR_REG_ALPHANUMERIC_KEY="^([a-zA-Z0-9]+\\s)*[a-zA-Z0-9]+$";
}