package com.wfc.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hui.guo
 * @since 2024/9/10 上午11:37
 */
public class StreamTest {


    @Test
    public void testGroupSort() {
        List<PreReleaseGiftPackDTO> list = Lists.newArrayList();
        PreReleaseGiftPackDTO  a1 = new PreReleaseGiftPackDTO();
        a1.setBrandId(2L);
        a1.setHiCardAmount(100L);
        list.add(a1);

        PreReleaseGiftPackDTO  a2 = new PreReleaseGiftPackDTO();
        a2.setBrandId(2L);
        a2.setHiCardAmount(200L);
        list.add(a2);

        PreReleaseGiftPackDTO  a3 = new PreReleaseGiftPackDTO();
        a3.setBrandId(2L);
        a3.setHiCardAmount(50L);
        list.add(a3);

        PreReleaseGiftPackDTO  b1 = new PreReleaseGiftPackDTO();
        b1.setBrandId(5L);
        b1.setHiCardAmount(300L);
        list.add(b1);

        PreReleaseGiftPackDTO  c1 = new PreReleaseGiftPackDTO();
        c1.setBrandId(1L);
        c1.setHiCardAmount(10L);
        list.add(c1);

        System.out.println(JSON.toJSONString(list));

        list = sortByGroup(list);

        System.out.println(JSON.toJSONString(list));
    }

    private static List<PreReleaseGiftPackDTO> sortByGroup(List<PreReleaseGiftPackDTO> list) {
        // 1. 按 brandId 分组
        Map<Long, List<PreReleaseGiftPackDTO>> groupedByBrandId = list.stream()
                .collect(Collectors.groupingBy(PreReleaseGiftPackDTO::getBrandId));

        // 2. 对每个分组内的元素按 amount 降序排序
        Map<Long, List<PreReleaseGiftPackDTO>> sortedByAmount = new HashMap<>();
        groupedByBrandId.forEach((brandId, dtoList) -> {
            sortedByAmount.put(brandId, dtoList.stream()
                    .sorted((dto1, dto2) -> dto2.getHiCardAmount().compareTo(dto1.getHiCardAmount()))
                    .collect(Collectors.toList()));
        });

        // 3. 按照原始顺序重新构建列表
        List<PreReleaseGiftPackDTO> resultList = new ArrayList<>();
        for (PreReleaseGiftPackDTO dto : list) {
            resultList.add(sortedByAmount.get(dto.getBrandId()).remove(0));
        }

        return resultList;
    }


    class PreReleaseGiftPackDTO {
        private Long brandId;
        private Long hiCardAmount;

        public Long getBrandId() {
            return brandId;
        }

        public void setBrandId(Long brandId) {
            this.brandId = brandId;
        }

        public Long getHiCardAmount() {
            return hiCardAmount;
        }

        public void setHiCardAmount(Long hiCardAmount) {
            this.hiCardAmount = hiCardAmount;
        }
    }
}
