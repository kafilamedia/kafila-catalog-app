package id.sch.kafila.catalog.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dimension implements Serializable {
    private int x, y, height, width;
    private int allMargin, marginTop, marginBottom, marginLeft, marginRight;
    private int allPadding, paddingLeft, paddingRight, paddingTop, paddingBottom;
}
