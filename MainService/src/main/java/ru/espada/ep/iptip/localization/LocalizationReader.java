package ru.espada.ep.iptip.localization;

import java.io.File;
import java.util.Map;

public interface LocalizationReader {

    Map<String, String> readLocalization(File file);

}
