package com.homemaker.Accounts.utils;

import com.homemaker.Accounts.service.CommonService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StreamFiltering {

    public static List<String> PROG_MGR_SUP_CARDBLK_REASONS_LIST= Arrays.asList();
    public static List<String> PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_COMMON= Arrays.asList();
    public static List<String> PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_VIRTUAL= Arrays.asList();
    public static List<String> PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_PHYSICAL= Arrays.asList();
    public static String SMUI_CUST_DETAIL_API_KEY_IS_VIRTUAL= "SMUI_CUST_DETAIL_API_KEY_IS_VIRTUAL";
    public static String COMMON= "COMMON";
    public static String YES= "YES";
    public static String NO= "NO";


    private void filterCardBlockReason(Map<String, Object> cardBlockReasons) {
        CommonService.logInfo(" entering into filterCardBlockReason() ");

        if (cardBlockReasons.containsKey("CARD_BLOCK_REASON_KEY")) {
            List<Map<String, Object>> cardReasonList = (List<Map<String, java.lang.Object>>) cardBlockReasons.get("CARD_BLOCK_REASON_KEY");
            CommonService.logInfo(" cardReasonList :  " + cardReasonList);

            cardReasonList = cardReasonList.stream()
                    .filter(reasonMap -> PROG_MGR_SUP_CARDBLK_REASONS_LIST.contains(reasonMap.get("CARDBLK_DESCRIPTION").toString()))
                    .collect(Collectors.toList());

            Consumer<Map<String, Object>> filteringForIsVirtual = cardReason -> {
                if(PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_COMMON.contains(cardReason.get("CARDBLK_DESCRIPTION"))){
                    cardReason.put(SMUI_CUST_DETAIL_API_KEY_IS_VIRTUAL,COMMON);
                } else if(PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_VIRTUAL.contains(cardReason.get("CARDBLK_DESCRIPTION"))){
                    cardReason.put(SMUI_CUST_DETAIL_API_KEY_IS_VIRTUAL,YES);
                }else if(PROG_MGR_SUP_CARDBLK_REASONS_LIST_FOR_PHYSICAL.contains(cardReason.get("CARDBLK_DESCRIPTION"))){
                    cardReason.put(SMUI_CUST_DETAIL_API_KEY_IS_VIRTUAL,NO);
                }
            };

            List<Map<String, Object>> cardReasonFilteredList = cardBlockReasonsFilteredList(cardReasonList, filteringForIsVirtual);

            CommonService.logInfo("Filtered List " + cardReasonFilteredList);

            //Sorting the list
            Collections.sort(cardReasonFilteredList, (a, b) -> a.get("CARDBLK_DESCRIPTION").toString().compareToIgnoreCase(b.get("CARDBLK_DESCRIPTION").toString()));
            cardBlockReasons.put("CARD_BLOCK_REASON_KEY", cardReasonFilteredList);
        }
        CommonService.logInfo(" exiting from filterCardBlockReason() ");
    }

    private static List<Map<String, Object>> cardBlockReasonsFilteredList(List<Map<String, Object>> cardReasonList,
                                                                          Consumer<Map<String, Object>> action) {
        cardReasonList.stream().forEach(action);
        return cardReasonList;
    }
}
