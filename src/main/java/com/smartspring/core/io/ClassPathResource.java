package com.smartspring.core.io;

import com.smartspring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path=null;
    private ClassLoader classLoader=null;

    public ClassPathResource(String path,ClassLoader classLoader) {
        this.path=path;
        this.classLoader=classLoader!=null?classLoader:ClassUtils.getDefaultClassLoader();
    }

    public ClassPathResource(String path) {
        this(path,(ClassLoader) null);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(this.path);
        if (is==null) {
            throw new FileNotFoundException(path+" cannot be opened");
        }
        return is;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
