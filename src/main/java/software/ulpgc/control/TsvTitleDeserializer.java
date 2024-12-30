package software.ulpgc.control;

import software.ulpgc.model.Title;

public class TsvTitleDeserializer implements TitleDeserializer{
    @Override
    public Title deserialize(String content) {
        String[] fields = content.split("\t");
        return new Title(fields[0], Title.TitleType.valueOf(capitalize(fields[1]), fields[2]));
    }

    private String capitalize(String field) {
        return field.substring(0,1).toUpperCase() + field.substring(1);
    }
}
