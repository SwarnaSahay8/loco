package assignment.transformer;

import assignment.utils.JsonUtil;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {

    @Override
    public String render(Object obj) {
        return JsonUtil.serialize(obj);
    }
}
