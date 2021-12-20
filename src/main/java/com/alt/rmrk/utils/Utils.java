package com.alt.rmrk.utils;

import com.alt.rmrk.models.Nfts;
import com.alt.rmrk.models.Resources_parts;
import com.alt.rmrk.service.URL;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Utils {

    public String formatImageSource(Integer id){
        String base_url=null;
        String complete_url=null;
        String base_sf="8949162-e0b9bdcc456a36497a-KANBIRD-KANS-0000000";
        String base_f="8949162-e0b9bdcc456a36497a-KANBIRD-KANF-000000";
        String base_r="8949162-e0b9bdcc456a36497a-KANBIRD-KANR-00000";
        String base_le_one="8949162-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_two="8949167-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_three="8949171-e0b9bdcc456a36497a-KANBIRD-KANL-0000";

        if(id < 10){
            base_url = base_sf;
        } else if (id >9 && id < 100){
            base_url = base_f;
        } else if (id > 99 && id < 1000){
            base_url = base_r;
        } else if (id > 999 && id < 3535){
            base_url = base_le_one;
        } else if(id > 3534 && id < 6949){
            base_url = base_le_two;
        } else if(id > 6948 && id < 10000){
            base_url = base_le_three;
        }

        return complete_url="https://kanaria-img.rmrk.link/1633635008653/jpeg/".concat(base_url).concat(String.valueOf(id)+".jpg");
    }

    public String formatURIForAPI(Integer id){
        String base_url=null;
        String complete_url=null;
        String base_sf="8949162-e0b9bdcc456a36497a-KANBIRD-KANS-0000000";
        String base_f="8949162-e0b9bdcc456a36497a-KANBIRD-KANF-000000";
        String base_r="8949162-e0b9bdcc456a36497a-KANBIRD-KANR-00000";
        String base_le_one="8949162-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_two="8949167-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_three="8949171-e0b9bdcc456a36497a-KANBIRD-KANL-0000";

        if(id < 10){
            base_url = base_sf;
        } else if (id >9 && id < 100){
            base_url = base_f;
        } else if (id > 99 && id < 1000){
            base_url = base_r;
        } else if (id > 999 && id < 3535){
            base_url = base_le_one;
        } else if(id > 3534 && id < 6949){
            base_url = base_le_two;
        } else if(id > 6948 && id < 10000){
            base_url = base_le_three;
        }

        return complete_url="https://kanaria.rmrk.app/api/nft/".concat(base_url).concat(String.valueOf(id));
    }

    public String formatURLForDB(String apiURI){
        return apiURI.replace("/api/nft/","/catalogue/");
    }

    public String formatURL(Integer id, URL option){
        String base_url=null;
        String complete_url=null;
        String base_sf="8949162-e0b9bdcc456a36497a-KANBIRD-KANS-0000000";
        String base_f="8949162-e0b9bdcc456a36497a-KANBIRD-KANF-000000";
        String base_r="8949162-e0b9bdcc456a36497a-KANBIRD-KANR-00000";
        String base_le_one="8949162-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_two="8949167-e0b9bdcc456a36497a-KANBIRD-KANL-0000";
        String base_le_three="8949171-e0b9bdcc456a36497a-KANBIRD-KANL-0000";

        if(id < 10){
            base_url = base_sf;
        } else if (id >9 && id < 100){
            base_url = base_f;
        } else if (id > 99 && id < 1000){
            base_url = base_r;
        } else if (id > 999 && id < 3535){
            base_url = base_le_one;
        } else if(id > 3534 && id < 6949){
            base_url = base_le_two;
        } else if(id > 6948 && id < 10000){
            base_url = base_le_three;
        }

        switch(option){
            case TG:
                complete_url="https://kanaria-img.rmrk.link/1633635008653/jpeg/".concat(base_url).concat(String.valueOf(id)+".jpg");
                break;
            case API:
                complete_url="https://kanaria.rmrk.app/api/nft/".concat(base_url).concat(String.valueOf(id));
                break;
            case DB:
                complete_url="https://kanaria.rmrk.app/catalogue/".concat(base_url).concat(String.valueOf(id));
                break;
            default:
                break;
        }
        return complete_url;
    }

    public Integer isFullset(Nfts birdData){

        Integer numParts = birdData.getResources().get(0).getResources_parts().size();
        List<Resources_parts> listPart = birdData.getResources().get(0).getResources_parts();
        String a[]
                = new String[] { "head","handleft","handright","wingright","wingleft","body","footleft","footright","tail"};
        List<String> partsOfSet = Arrays.asList(a);
        String set = "";
        Boolean fullset = false;
        Integer partsMissing = 8;

        for (int i=0; i<numParts; i++){

            String part = listPart.get(i).getPart().getPart_id();
            if(part.contains("_")){
                String part_name = (part.substring(part.lastIndexOf("_")+1,part.length())).toLowerCase();
                String part_set= (part.substring(0,part.lastIndexOf("_"))).toLowerCase();

                if(partsOfSet.contains(part_name) && set.isEmpty()){
                    set = part_set;
                    if(set.contains("var")) break;
                    partsMissing--;
                } else if (partsOfSet.contains(part_name) && set != null && partsMissing >= 0){
                    if(partsMissing >=0){
                        if(set.equals(part_set)){
                            if(partsMissing == 0){
                                fullset = Boolean.TRUE;
                                //System.out.println("Bird has fullset");
                                return 1;
                            }
                            partsMissing--;
                            continue;
                        }else {
                            break;
                        }
                    }else {
                        if (!partsOfSet.contains(part_name)){
                            break;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    public String getSetName(Nfts birdData) {
        Integer numParts = birdData.getResources().get(0).getResources_parts().size();
        List<Resources_parts> listPart = birdData.getResources().get(0).getResources_parts();
        String a[]
                = new String[] { "head","handleft","handright","wingright","wingleft","body","footleft","footright","tail"};
        List<String> partsOfSet = Arrays.asList(a);
        String set = "";
        Boolean fullset = false;
        Integer partsMissing = 8;

        for (int i=0; i<numParts; i++){

            String part = listPart.get(i).getPart().getPart_id();
            if(part.contains("_")){
                String part_name = (part.substring(part.lastIndexOf("_")+1,part.length())).toLowerCase();
                String part_set= (part.substring(0,part.lastIndexOf("_"))).toLowerCase();

                if(partsOfSet.contains(part_name) && set.isEmpty()){
                    set = part_set;
                    return set;
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    public Integer calculateFinalPrice(Long original){
        Long originalPrice = (original/1000000000000L);
        Double finalPrice = Math.ceil(originalPrice.doubleValue() * 1.052631579);
        return finalPrice.intValue();
    }
}
