package software.ulpgc.control;

import software.ulpgc.model.Title;

public interface TitleDeserializer {
    Title deserialize(String content);
}
