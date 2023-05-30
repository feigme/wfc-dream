package com.wfc.bpc.builder.model;

import lombok.Getter;

/**
 * @author hui.guo
 * @since 2023/5/30 10:19 上午
 */
@Getter
public class BpcDoublyLinkedModel {

    private BpcModel head;

    private String name;

    public BpcDoublyLinkedModel(String name, BpcModel model) {
        head = model;
        this.name = name;
    }

    public void append(BpcModel model) {
        if (head == null) {
            head = model;
        } else {
            BpcModel current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = model;
            model.prev = current;
        }
    }

    public String print() {
        return print(0, false);
    }

    private String print(int n, boolean hasNext) {
        BpcModel current = head;
        StringBuilder sb = new StringBuilder();
        if (n > 0) {
            if (hasNext) {
                sb.append("├── ");
            } else {
                sb.append("└── ");
            }
        }
        sb.append(name).append("\n");
        while (current != null) {
            if (current instanceof SerialValveModel) {
                SerialValveModel serModel = (SerialValveModel) current;
                if (n > 0) {
                    if (hasNext) {
                        sb.append(String.format("│%-" + n * 3 + "s", ""));
                    } else {
                        sb.append(String.format("%-" + n * 4 + "s", ""));
                    }
                }
                if (serModel.next != null) {
                    sb.append("├── ");
                } else {
                    sb.append("└── ");
                }
                sb.append(serModel.name).append(" ").append(serModel.getValve()).append("\n");
            } else if (current instanceof ParallelValveModel) {
                ParallelValveModel parModel = (ParallelValveModel) current;

                for (BpcDoublyLinkedModel bpcDoublyLinkedModel : parModel.getValveList()) {
                    sb.append(bpcDoublyLinkedModel.print(n + 1, current.next != null));
                }
            }

            current = current.next;
        }
        return sb.toString();
    }


}
