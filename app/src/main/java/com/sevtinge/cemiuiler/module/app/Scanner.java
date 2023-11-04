package com.sevtinge.cemiuiler.module.app;

import com.sevtinge.cemiuiler.module.base.BaseModule;
import com.sevtinge.cemiuiler.module.hook.scanner.EnableCard;
import com.sevtinge.cemiuiler.module.hook.scanner.EnableDocPpt;
import com.sevtinge.cemiuiler.module.hook.scanner.EnableOcr;
import com.sevtinge.cemiuiler.module.hook.scanner.EnableTranslation;
import com.sevtinge.cemiuiler.module.hook.scanner.document.EnableDocument;
import com.sevtinge.cemiuiler.module.hook.scanner.document.EnableExcel;
import com.sevtinge.cemiuiler.module.hook.scanner.document.EnablePpt;

public class Scanner extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new EnableOcr(), mPrefsMap.getBoolean("scanner_ocr"));
        initHook(new EnableExcel(), mPrefsMap.getBoolean("scanner_excel"));
        initHook(new EnablePpt(), mPrefsMap.getBoolean("scanner_ppt"));
        initHook(new EnableCard(), mPrefsMap.getBoolean("scanner_card"));
        initHook(new EnableTranslation(), mPrefsMap.getBoolean("scanner_translation"));
        initHook(new EnableDocument(), mPrefsMap.getBoolean("scanner_document"));
        initHook(new EnableDocPpt(), mPrefsMap.getBoolean("scanner_doc_ppt"));
    }
}


