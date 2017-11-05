package com.athenahealth.mdphackathon.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.athenahealth.mdphackathon.entity.*;

import java.io.IOException;
import java.util.*;

public class Bestimator {

    private static String json = "{ \"*SELF PAY*\": { \"99201\": 1, \"99202\": 2, \"8\": 51, \"99214\": 1, \"99490\": 2 },\n"
        + "  \"CHARGE CAPTURE INSURANCE PACKAGE (MOVED-TO-HOLD)\": { \"92960\": 4, \"93010\": 4, \"99359\": 1 },\n"
        + "  \"CIGNA HEALTHCARE (HMO)\": \n"
        + "   { \"10021\": 1,\n"
        + "     \"20610\": 1,\n"
        + "     \"99490\": 3,\n"
        + "     \"99396,25,59\": 2,\n"
        + "     \"1000F\": 1,\n"
        + "     \"0518F\": 6 },\n"
        + "  \"QUAL CHOICE OF ARKANSAS (HMO)\": { \"99202\": 4 },\n"
        + "  \"BLUE CROSS-CA: ANTHEM BCBS (PPO)\": { \"99490\": 2 },\n"
        + "  \"ATTORNEY RICHARD D. GIGLIO\": { \"99213\": 3 },\n"
        + "  \"HUMANA (PPO)\": { \"11440\": 1 },\n"
        + "  \"ALLIANZ LIFE INSURANCE CO\": { \"99490\": 1 },\n"
        + "  \"BCBS-ME:ANTHEM BLUE CROSS BLUE SHIELD -> 1663\": { \"99213\": 3 },\n"
        + "  \"MEDICARE B-GA: CAHABA GBA\": { \"99490\": 3 },\n"
        + "  \"INGENIOUSMED (MOVED TO HOLD)\": { \"99221\": 1, \"99223\": 2 },\n"
        + "  \"AETNA -> 3347\": { \"0518F\": 1 } }";

    private static ObjectMapper mapper = new ObjectMapper();

    private static Map<String, Map<String, Integer>> jsonMap;
    private static Map<String, Integer> cptToTotalClaimCountMap = new HashMap<String, Integer>();

    private static Set<String> insurances;

    static {
        try {
            jsonMap = mapper.readValue(json, new TypeReference<Map<String, Map<String, Integer>>>(){});
            insurances = jsonMap.keySet();

            Iterator<String> insuranceIterator = insurances.iterator();
            while(insuranceIterator.hasNext()) {
                String thisInsurance = insuranceIterator.next();
                Map<String, Integer> cptToCountMap = jsonMap.get(thisInsurance);

                Set<String> cptSet = cptToCountMap.keySet();
                Iterator<String> cptIter = cptSet.iterator();
                while(cptIter.hasNext()) {
                    String thisCpt = cptIter.next();
                    if(cptToTotalClaimCountMap.get(thisCpt) == null) {
                        cptToTotalClaimCountMap.put(thisCpt, cptToCountMap.get(thisCpt));
                    }
                    else {
                        cptToTotalClaimCountMap.put(thisCpt, cptToTotalClaimCountMap.get(thisCpt) + cptToCountMap.get(thisCpt));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bestimate getBestimate(String cpt, String insurance) {
        int universalClaimCount;
        if (cptToTotalClaimCountMap.containsKey(cpt)) {
            universalClaimCount = cptToTotalClaimCountMap.get(cpt);
        } else {
            universalClaimCount = -1;
        }
        int thisInsuranceClaimCount;
        if (jsonMap.containsKey(insurance) && jsonMap.get(insurance).containsKey(cpt)) {
            thisInsuranceClaimCount = jsonMap.get(insurance).get(cpt);
        } else {
            thisInsuranceClaimCount = -1;
        }
        return new Bestimate(universalClaimCount, thisInsuranceClaimCount, insurance, cpt);
    }

}
