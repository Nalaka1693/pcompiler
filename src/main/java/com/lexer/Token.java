package com.lexer;

public class Token {    
    private TokenClass tokenClass;
    private String value;
    private int lineNum;
    private int columnNum;
    
    /** Token constructor
     * @param tokenClass
     * @param value
     * @param lineNum
     * @param columnNum
     * @return 
     */
    public Token(TokenClass tokenClass, String value, int lineNum, int columnNum) {
        this.tokenClass = tokenClass;
        this.value = value;
        this.lineNum = lineNum;
        this.columnNum = columnNum;
    }
    
    public TokenClass getTokenClass() {
        return this.tokenClass;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public int getLineNum() {
        return this.lineNum;
    }
    
    public int getColumnNum() {
        return this.columnNum;
    }
}