package li.tau.beusable.challenge.repository;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

@Slf4j
public class IntArrayJsonConverter implements AttributeConverter<int[], String> {

    private static final Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(int[] attribute) {
        String json = null;
        if (attribute != null) {
            json = GSON.toJson(attribute);
        }

        log.debug("Converted {} to json: {}", attribute, json);

        return json;
    }

    @Override
    public int[] convertToEntityAttribute(String dbData) {
        log.trace("dbData: {}", dbData);

        int[] array;
        if (StringUtils.isBlank(dbData)) {
            array = new int[0];
        } else {
            array = GSON.fromJson(dbData, int[].class);
        }
        
        log.debug("Converted {} to int array: {}", dbData, array);
        
        return array;
    }

}
