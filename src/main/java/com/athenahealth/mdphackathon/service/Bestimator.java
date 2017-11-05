package com.athenahealth.mdphackathon.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.athenahealth.mdphackathon.entity.*;

import java.io.IOException;
import java.util.*;

public class Bestimator {

    private static String json = "{ \"*SELF PAY*\": \n"
        + "   { \"29125\": 158,\n"
        + "     \"99201\": 1,\n"
        + "     \"99202\": 5,\n"
        + "     \"99213\": 59,\n"
        + "     \"99214\": 1,\n"
        + "     \"99442\": 1,\n"
        + "     \"99490\": 4 },\n"
        + "  \"CIGNA HEALTHCARE (HMO)\": \n"
        + "   { \"10021\": 1,\n"
        + "     \"10180\": 1,\n"
        + "     \"20610\": 1,\n"
        + "     \"99490\": 4,\n"
        + "     \"99396,25,59\": 2,\n"
        + "     \"1000F\": 1,\n"
        + "     \"0518F\": 6 },\n"
        + "  \"CHARGE CAPTURE INSURANCE PACKAGE (MOVED-TO-HOLD)\": { \"92960\": 5, \"93010\": 5, \"99359\": 1 },\n"
        + "  \"HUMANA (PPO)\": { \"10040\": 1, \"11440\": 1, \"99213\": 2, \"99490\": 2 },\n"
        + "  \"ATTORNEY RICHARD D. GIGLIO\": { \"99213\": 7 },\n"
        + "  \"QUAL CHOICE OF ARKANSAS (HMO)\": { \"99202\": 6, \"99213\": 1 },\n"
        + "  \"DENTAL INTEGRATION MEDICARE (MOVED-HOLD)\": { \"82040\": 1 },\n"
        + "  \"BCBS-ME:ANTHEM BLUE CROSS BLUE SHIELD -> 1663\": { \"99213\": 4 },\n"
        + "  \"MEDICARE B-GA: CAHABA GBA\": { \"99490\": 5 },\n"
        + "  \"BLUE CROSS-CA: ANTHEM BCBS (PPO)\": { \"99490\": 3 },\n"
        + "  \"ALLIANZ LIFE INSURANCE CO\": { \"99213\": 1, \"99490\": 1 },\n"
        + "  \"BCBS-NY: EMPIRE BCBS - OUT OF STATE - BLUE CARD PROGRAM\": { \"99202\": 1 },\n"
        + "  \"PAYMENT PLAN\": { \"99202\": 1 },\n"
        + "  \"MEDICARE B-ME: NATIONAL GOVERNMENT SERVICES\": { \"99490\": 1 },\n"
        + "  \"INGENIOUSMED (MOVED TO HOLD)\": { \"99221\": 4, \"99223\": 3 },\n"
        + "  \"AETNA -> 3347\": { \"1000F\": 1, \"0518F\": 1 },\n"
        + "  \"CIGNA HEALTHCARE\": { \"0201\": 2 },\n"
        + "  \"BCBS-MA: BCBS (PPO)\": { \"0250\": 2 } }";

    private static String jsonProceduresByState = "{ \"MA\": \n"
        + "   { \"11440\": 1,\n"
        + "     \"20610\": 1,\n"
        + "     \"29125\": 4,\n"
        + "     \"99202\": 2,\n"
        + "     \"99213\": 6,\n"
        + "     \"99490\": 11,\n"
        + "     \"99396,25,59\": 2,\n"
        + "     \"0201\": 2 },\n"
        + "  \"KS\": { \"29125\": 4 },\n"
        + "  \"KY\": { \"29125\": 5, \"99202\": 2, \"99213\": 47 },\n"
        + "  \"AL\": { \"29125\": 7, \"99213\": 2 },\n"
        + "  \"NM\": { \"29125\": 4 },\n"
        + "  \"GA\": \n"
        + "   { \"29125\": 2,\n"
        + "     \"92960\": 5,\n"
        + "     \"93010\": 5,\n"
        + "     \"99213\": 2,\n"
        + "     \"99214\": 1,\n"
        + "     \"99359\": 1,\n"
        + "     \"99442\": 1,\n"
        + "     \"99490\": 1 },\n"
        + "  \"MO\": { \"29125\": 6 },\n"
        + "  \"RI\": { \"29125\": 3 },\n"
        + "  \"VA\": { \"29125\": 6, \"99213\": 1 },\n"
        + "  \"CA\": { \"29125\": 4, \"99201\": 1, \"99202\": 1, \"99213\": 1, \"99490\": 4, \"10021\": 1, \"10180\": 1, \"1000F\": 1, \"0518F\": 6},\n"
        + "  \"MD\": { \"29125\": 4 },\n"
        + "  \"TX\": { \"29125\": 1, \"82040\": 1 },\n"
        + "  \"HI\": { \"29125\": 2, \"99202\": 1 },\n"
        + "  \"IN\": { \"10040\": 1, \"29125\": 5, \"99202\": 1 },\n"
        + "  \"DE\": { \"29125\": 5 },\n"
        + "  \"CO\": { \"29125\": 2 },\n"
        + "  \"SC\": { \"29125\": 1 },\n"
        + "  \"ME\": { \"29125\": 3 },\n"
        + "  \"NC\": { \"29125\": 4 },\n"
        + "  \"NY\": { \"29125\": 2, \"99202\": 1 },\n"
        + "  \"AZ\": { \"29125\": 3 },\n"
        + "  \"UT\": { \"29125\": 6 },\n"
        + "  \"FL\": { \"29125\": 6 },\n"
        + "  \"SD\": { \"29125\": 3 },\n"
        + "  \"OH\": { \"29125\": 2, \"1000F\": 1, \"0518F\": 1, \"0250\": 2 },\n"
        + "  \"TN\": { \"29125\": 2, \"99490\": 4 },\n"
        + "  \"ID\": { \"29125\": 3 },\n"
        + "  \"MI\": { \"29125\": 2 },\n"
        + "  \"PA\": { \"29125\": 1 },\n"
        + "  \"AR\": { \"29125\": 5 },\n"
        + "  \"VT\": { \"29125\": 4 },\n"
        + "  \"IL\": { \"29125\": 3 },\n"
        + "  \"MT\": { \"29125\": 4 },\n"
        + "  \"AK\": { \"29125\": 2 },\n"
        + "  \"IA\": { \"29125\": 2 },\n"
        + "  \"NV\": { \"29125\": 3 },\n"
        + "  \"ND\": { \"29125\": 1 },\n"
        + "  \"CT\": { \"29125\": 3 },\n"
        + "  \"NH\": { \"29125\": 3 },\n"
        + "  \"NE\": { \"29125\": 4 },\n"
        + "  \"WY\": { \"29125\": 2 },\n"
        + "  \"WV\": { \"29125\": 4 },\n"
        + "  \"DC\": { \"29125\": 4 },\n"
        + "  \"MS\": { \"29125\": 2 },\n"
        + "  \"WI\": { \"29125\": 2 },\n"
        + "  \"LA\": { \"29125\": 1 },\n"
        + "  \"WA\": { \"29125\": 3 },\n"
        + "  \"MN\": { \"29125\": 1 },\n"
        + "  \"NJ\": { \"99221\": 3, \"99223\": 3 },\n"
        + "  \"OR\": { \"29125\": 1 },\n"
        + "  \"OK\": { \"29125\": 2 } }";

    private static ObjectMapper mapper = new ObjectMapper();

    private static Map<String, Map<String, Integer>> mapInsuranceToCptToCount;
    private static Map<String, Integer> cptToTotalClaimCountMap = new HashMap<>();

    private static Map<String, Map<String, Integer>> mapStateToCptToCount = new HashMap<>();

    private static Set<String> insurances;

    static {
        try {
            mapInsuranceToCptToCount = mapper.readValue(json, new TypeReference<Map<String, Map<String, Integer>>>(){});
            insurances = mapInsuranceToCptToCount.keySet();
            mapStateToCptToCount = mapper.readValue(jsonProceduresByState, new TypeReference<Map<String, Map<String, Integer>>>(){});

            Iterator<String> insuranceIterator = insurances.iterator();
            while(insuranceIterator.hasNext()) {
                String thisInsurance = insuranceIterator.next();
                Map<String, Integer> cptToCountMap = mapInsuranceToCptToCount.get(thisInsurance);

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

    public static Map<String, Map<String, Integer>> getStateMap() {
      return mapStateToCptToCount;
    }

    public static Bestimate getBestimate(String cpt, String insurance) {
        int universalClaimCount;
        if (cptToTotalClaimCountMap.containsKey(cpt)) {
            universalClaimCount = cptToTotalClaimCountMap.get(cpt);
        } else {
            universalClaimCount = -1;
        }
        int thisInsuranceClaimCount;
        if (mapInsuranceToCptToCount.containsKey(insurance) && mapInsuranceToCptToCount.get(insurance).containsKey(cpt)) {
            thisInsuranceClaimCount = mapInsuranceToCptToCount.get(insurance).get(cpt);
        } else {
            thisInsuranceClaimCount = -1;
        }
        return new Bestimate(universalClaimCount, thisInsuranceClaimCount, insurance, cpt);
    }

}
