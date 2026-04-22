package model;

import lombok.Data;

@Data
public class Marking {
    private Integer id;
    private String date;
    private String injectionSite;
    private String number;
    private String type;
}
