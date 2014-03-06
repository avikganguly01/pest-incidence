package org.nbaii.pic.handler;

public enum ImportFormatType {
	
    XLS ("application/vnd.ms-excel");
    
    private final String format;

    private ImportFormatType(String format) {
        this.format= format;
    }

    public String getFormat() {
        return format;
    }
    
    public static ImportFormatType of(String name) {
        for(ImportFormatType type : ImportFormatType.values()) {
            if(type.getFormat().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Only excel files accepted! provided : " +name );
    }
}