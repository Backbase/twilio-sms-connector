package com.backbase.communication.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Converter to convert a properties string to a Range class
 * <p>
 * Valid syntax is x-y or just x, where x and y are integers
 *
 * @see MessageChannelProperties
 */
@Component
@ConfigurationPropertiesBinding
@Slf4j
public class MessageChannelPropertiesConverter implements Converter<String, Range<Integer>> {

    Pattern pattern = Pattern.compile("^\\d+(-\\d+)?$");

    @Override
    public Range<Integer> convert(String range) {
        if (!pattern.matcher(range).matches()) {
            log.error(String.format("Range '%s' does not match x[-y], check application properties", range));
            throw new IllegalArgumentException(String.format("Range %s does not match x[-y]", range));
        }
        if (range.contains("-")) {
            String[] data = range.split("-");
            return Range.between(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        } else {
            return Range.between(Integer.parseInt(range), Integer.parseInt(range));
        }
    }
}
