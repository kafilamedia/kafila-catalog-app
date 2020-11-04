package id.sch.kafila.catalog.handlers;

import id.sch.kafila.catalog.activities.BaseActivity;
import lombok.Data;

@Data
public class BaseHandler <P extends BaseActivity> {
    private P page;
}
