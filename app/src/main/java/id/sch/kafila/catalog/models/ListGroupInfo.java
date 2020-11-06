package id.sch.kafila.catalog.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListGroupInfo {
    private String name;
    private ArrayList<ListChildInfo> childInfos;
}
