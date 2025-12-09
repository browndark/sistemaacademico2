package org.gerenciador_de_sistemas.utils;

import java.nio.file.Paths;

public class PatchFXML {
    public static String patchFXML(){
        String path = "src/main/java/org/gerenciador_de_sistemas/view";
        return Paths.get(path).toAbsolutePath().toString();
    }

}
