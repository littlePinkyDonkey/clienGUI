package client;

import java.io.File;
import java.io.Serializable;

public class DataPackage implements Serializable {
    private String command;
    private File file = null;

    public DataPackage(){}
    public DataPackage(String command){
        this.command = command;
    }
    public DataPackage(String command, File file){
        this.command = command;
        this.file = file;
    }

    public String getCommand() {
        return command;
    }
    public File getFile() {
        return file;
    }
}
