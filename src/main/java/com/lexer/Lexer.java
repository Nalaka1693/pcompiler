package com.lexer;

import java.util.ArrayList;


enum TokenClass
    {
        END_OF_INPUT, // ""
        OP_ADD, // +
        OP_SUBTRACT, // -
        OP_MULTIPLY, // *
        OP_DIVIDE, // /
        OP_MOD, //  %
        OP_NEGATE, // -
        OP_NOT, // !
        OP_LESS, // <
        OP_LESSEQUAL, // <=
        OP_GREATER, // >
        OP_GREATEREQUAL, // >=
        OP_EQUAL, // ==
        OP_NOTEQUAL, // != 
        OP_ASSIGN, // =
        OP_AND, // &&
        OP_OR, // ||
        OP_BITWISEAND, // &
        OP_BITWISEOR, // |
        OP_BITWISEXOR, // ^
        OP_INCREMENTONE, // ++
        OP_DECREMENTONE, // --
        OP_COMPUNDADD, // +=
        OP_COMPUNDSUBTRACT, // -=
        OP_COMPUNDMULTIPLY, // *=
        OP_COMPUNDDIVIDE, // /=
        OP_COMPUNDMOD, // %=
        KEYWORD_FUNCTION, // function
        KEYWORD_RETURN, // return
        KEYWORD_IF, // if
        KEYWORD_ELSE, // else
        KEYWORD_WHILE, // while
        KEYWORD_INT, // int
        KEYWORD_STR, // str
        KEYWORD_BOOL, // bool
        KEYWORD_DOUBLE, // double
        KEYWORD_TRUE, // true
        KEYWORD_FALSE, // false
        KEYWORD_INCLUDE, // include
        LEFTPAREN, // (
        RIGHTPAREN, // )
        LEFTBRACE, // {
        RIGHTBRACE, // }
        LEFTSQEBRACKET, // [
        RIGHTSQEBRACKET, // ]
        SEMICOLON, // ;
        COLON, // :
        COMMA, // ,
        IDENTIFIER, // [_a-zA-Z][_a-zA-Z0-9]*
        DOUBLE, // [0-9]*\.[0-9]+
        INTEGER, // [0-9]+
        STRING // "[^"\n]*"
    };

public class Lexer {    
    String sourceCode = "";
    private int position = 0;
    private int lineNum = 1;
    private int columnNum = 1;

    
    /** Lexer construct 
     * @param sourceCode
     * @return 
     */
    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
    }
    
    
    /** Get the next char of the source code string
     * increment position ad column
     * @return char
     */
    private char nextChar() {
        try {
            this.position++;
            this.columnNum++;
            return this.sourceCode.charAt(this.position);
        } catch (StringIndexOutOfBoundsException e) {
            return '\0';
        }
    }

    
    /** Check for new line (line feed) and carriage return
     * chatacters to ignore and increment line number
     * @param chr
     * @return boolean
     */
    private static boolean isNewLine(char chr) {
        return chr == '\n' || chr == 13;
    }

    
    /** Check for blanks to ignore
     * @param chr
     * @return boolean
     */
    private static boolean isBlank(char chr) {
        int charCode = chr;
        return charCode == 9 || // horizontal tab
            charCode == 11 ||   // vertical tab
            charCode == 12 ||   // form feed 
            charCode == 32 ||   // space
            charCode == 160;    // non-breaking space
    }
    
    
    /** Double quote checking for string literals
     * @param chr
     * @return boolean
     */
    private static boolean isQuote(char chr) {
        return chr == '"';
    }
    
    
    /** Check for letter and underscore
     * for keywords and identifiers
     * @param chr
     * @return boolean
     */
    private static boolean isAlpha(char chr) {
        return (chr >= 'A' && chr <= 'Z') ||
            (chr >= 'a' && chr <= 'z') ||
            chr == '_';
    }

    
    /** 
     * @param chr
     * @return boolean
     */
    private static boolean isNumber(char chr) {
        return chr >= '0' && chr <= '9';
    }

    
    /** Check for single character operators
     * @param chr
     * @return boolean
     */
    private static boolean isOperator(char chr) {
        return chr == '*' ||
            chr == '/' ||
            chr == '+' ||
            chr == '-' ||
            chr == '%' ||
            chr == '!' ||
            chr == '=' ||
            chr == '|' ||
            chr == '&' ||
            chr == '^' ||
            chr == '>' ||
            chr == '<';
    }

    
    /** Analyse source code string to genatrate tokens 
     * this.nextChar() will be called to get the next char 
     * @return Token
     */
    private Token nextToken() {
        // while is for ignore mutiple blanks and new lines
        while (true) {
            // if the position is at the end of the string return end of input
            if (this.position >= this.sourceCode.length()) {
                return new Token(TokenClass.END_OF_INPUT, "", this.lineNum, this.columnNum);
            }
            
            char chr = this.sourceCode.charAt(this.position);

            if (Lexer.isBlank(chr)) {
                chr = this.nextChar();
                continue;
            }

            if (Lexer.isNewLine(chr)) {
                this.position++;
                this.lineNum++;
                // can't call nextChat(), for each line cloumn will be reset
                this.columnNum = 0;
                chr = this.sourceCode.charAt(this.position);
                continue;
            }
            
            // keywords and identifiers
            if (Lexer.isAlpha(chr)) {
                int start = this.position;
                
                // both should start with a letter or underscore
                while (true) {
                    chr = this.nextChar();

                    if (!Lexer.isAlpha(chr)) {
                        this.position--;
                        this.columnNum--;
                        break;
                    }      
                }
                
                // to collect the number part of the identifiers
                while (true) {
                    chr = this.nextChar();

                    if (!Lexer.isNumber(chr)) {
                        this.position--;
                        this.columnNum--;
                        break;
                    }
                }

                String value = sourceCode.substring(start, this.position + 1);

                if (value.equals("if")) {
                    return new Token(TokenClass.KEYWORD_IF, value, this.lineNum, this.columnNum);
                } else if (value.equals("else")) {
                    return new Token(TokenClass.KEYWORD_ELSE, value, this.lineNum, this.columnNum);
                } else if (value.equals("while")) {
                    return new Token(TokenClass.KEYWORD_WHILE, value, this.lineNum, this.columnNum);
                } else if (value.equals("int")) {
                    return new Token(TokenClass.KEYWORD_INT, value, this.lineNum, this.columnNum);
                } else if (value.equals("str")) {
                    return new Token(TokenClass.KEYWORD_STR, value, this.lineNum, this.columnNum);
                } else if (value.equals("bool")) {
                    return new Token(TokenClass.KEYWORD_BOOL, value, this.lineNum, this.columnNum);
                } else if (value.equals("double")) {
                    return new Token(TokenClass.KEYWORD_DOUBLE, value, this.lineNum, this.columnNum);
                } else if (value.equals("true")) {
                    return new Token(TokenClass.KEYWORD_TRUE, value, this.lineNum, this.columnNum);
                } else if (value.equals("false")) {
                    return new Token(TokenClass.KEYWORD_FALSE, value, this.lineNum, this.columnNum);
                } else if (value.equals("function")) {
                    return new Token(TokenClass.KEYWORD_FUNCTION, value, this.lineNum, this.columnNum);
                } else if (value.equals("return")) {
                    return new Token(TokenClass.KEYWORD_RETURN, value, this.lineNum, this.columnNum);
                } else if (value.equals("include")) {
                    return new Token(TokenClass.KEYWORD_INCLUDE, value, this.lineNum, this.columnNum);
                } else {
                    return new Token(TokenClass.IDENTIFIER, value, this.lineNum, this.columnNum);
                }
            }

            // integer and real number literals,'-' followed by a number is negetive 
            if (Lexer.isNumber(chr) || (chr == '-' && Lexer.isNumber(this.sourceCode.charAt(this.position + 1)))) {
                int start = this.position;
                boolean isDouble = false;

                if (chr == '-') {
                    return new Token(TokenClass.OP_NEGATE, "", this.lineNum, this.columnNum);
                }

                while (true) {
                    chr = this.nextChar();

                    if (!Lexer.isNumber(chr)) {
                        this.position--;
                        this.columnNum--;
                        break;
                    }
                }
                
                // to collect numbers after decinal point
                if (this.sourceCode.charAt(this.position + 1) == '.') {
                    this.nextChar();

                    while (true) {
                        chr = this.nextChar();
        
                        if (!Lexer.isNumber(chr)) {
                            this.position--;
                            this.columnNum--;
                            break;
                        }

                        isDouble = true;
                    }
                }

                String value = sourceCode.substring(start, this.position + 1);

                if (isDouble) {
                    return new Token(TokenClass.DOUBLE, value, this.lineNum, this.columnNum);
                } else {
                    return new Token(TokenClass.INTEGER, value, this.lineNum, this.columnNum);                
                }
            }

            // to collect string literals 
            if (Lexer.isQuote(chr)) {                
                this.nextChar();    // to ignore first double quote
                int start = this.position;

                while (true) {
                    chr = this.nextChar();

                    if (Lexer.isQuote(chr)) {
                        this.position--;
                        this.columnNum--;
                        break;
                    }
                }

                String value = sourceCode.substring(start, this.position + 1);
                this.nextChar();    // to ignore last double quote

                return new Token(TokenClass.STRING, value, this.lineNum, this.columnNum);
            }

            // single line comments, collect all until a new line 
            if (chr == '/' && this.sourceCode.charAt(this.position + 1) == '/') {
                boolean back = false;
                this.nextChar();

                while (true) {
                    chr = this.nextChar();

                    if (Lexer.isNewLine(chr)) {
                        this.lineNum++;
                        this.columnNum = 0;
                        back = true;
                        break;
                    } else if (chr == '\0') {   // end of source code
                        back = true;
                        break;
                    }
                }

                if (back) {
                    // when a new line hit go to top and continue
                    // no token retuned for the comments
                    continue;
                }
            }

            if (chr == '(') {
                return new Token(TokenClass.LEFTPAREN, "", this.lineNum, this.columnNum);
            } else if (chr == ')') {
                return new Token(TokenClass.RIGHTPAREN, "", this.lineNum, this.columnNum);
            } else if (chr == '[') {
                return new Token(TokenClass.LEFTSQEBRACKET, "", this.lineNum, this.columnNum);
            } else if (chr == ']') {
                return new Token(TokenClass.RIGHTSQEBRACKET, "", this.lineNum, this.columnNum);
            } else if (chr == '{') {
                return new Token(TokenClass.LEFTBRACE, "", this.lineNum, this.columnNum);
            } else if (chr == '}') {
                return new Token(TokenClass.RIGHTBRACE, "", this.lineNum, this.columnNum);
            } else if (chr == ':') {
                return new Token(TokenClass.COLON, "", this.lineNum, this.columnNum);
            } else if (chr == ',') {
                return new Token(TokenClass.COMMA, "", this.lineNum, this.columnNum);
            } else if (chr == ';') {
                return new Token(TokenClass.SEMICOLON, "", this.lineNum, this.columnNum);
            }

            // to collect operators
            if (Lexer.isOperator(chr)) {
                char second = this.sourceCode.charAt(this.position + 1);
                String compoundOp = Character.toString(chr) + Character.toString(second);

                if (chr == '+') {
                    if (compoundOp.equals("++")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_INCREMENTONE, "", this.lineNum, this.columnNum);
                    } else if (compoundOp.equals("+=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_COMPUNDADD, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_ADD, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '-') {
                    if (compoundOp.equals("--")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_DECREMENTONE, "", this.lineNum, this.columnNum);
                    } else if (compoundOp.equals("-=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_COMPUNDSUBTRACT, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_SUBTRACT, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '*') {
                    if (compoundOp.equals("*=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_COMPUNDMULTIPLY, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_MULTIPLY, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '/') {
                    if (compoundOp.equals("/=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_COMPUNDDIVIDE, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_DIVIDE, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '%') {
                    if (compoundOp.equals("%=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_COMPUNDMOD, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_MOD, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '<') {
                    if (compoundOp.equals("<=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_LESSEQUAL, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_LESS, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '>') {
                    if (compoundOp.equals(">=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_GREATEREQUAL, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_GREATER, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '<') {
                    if (compoundOp.equals("<=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_LESSEQUAL, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_LESS, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '=') {
                    if (compoundOp.equals("==")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_EQUAL, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_ASSIGN, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '!') {
                    if (compoundOp.equals("!=")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_NOTEQUAL, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_NOT, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '&') {
                    if (compoundOp.equals("&&")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_AND, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_BITWISEAND, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '|') {
                    if (compoundOp.equals("||")) {
                        this.nextChar();
                        return new Token(TokenClass.OP_OR, "", this.lineNum, this.columnNum);
                    } else {
                        return new Token(TokenClass.OP_BITWISEOR, "", this.lineNum, this.columnNum);
                    }
                } else if (chr == '^') {
                    return new Token(TokenClass.OP_BITWISEXOR, "", this.lineNum, this.columnNum);
                }
            }

            // didn't match any of the token clasess
            break;
        }
        
        return new Token(TokenClass.END_OF_INPUT, "", this.lineNum, this.columnNum);
    }

    
    /** Collect tokens to array list 
     * @return ArrayList<Token>
     */
    public ArrayList<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<Token>();

        Token token = this.nextToken();
        tokens.add(token);

        while (token.getTokenClass() != TokenClass.END_OF_INPUT) {
            this.nextChar();    // ignore the spaces after keywords identifiers
            token = this.nextToken();
            tokens.add(token);            
        }

        return tokens;
    }
}