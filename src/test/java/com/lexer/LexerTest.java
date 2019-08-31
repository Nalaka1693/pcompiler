package com.lexer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LexerTest {

    @Test
    public void emptyStringIgnoringTest() {
        TokenClass [] expectedResult = new TokenClass [] { TokenClass.END_OF_INPUT };

        String sourceCode = "";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void singleLineBlanksIgnoringTest() {
        TokenClass [] expectedResult = new TokenClass [] { TokenClass.END_OF_INPUT };

        String sourceCode = "             \t  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void manyLineBlanksIgnoringTest() {
        TokenClass [] expectedResult = new TokenClass [] { TokenClass.END_OF_INPUT };

        String sourceCode = "           \n  \t \n\n";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void onlyCommentIgnoringTest() {
        TokenClass [] expectedResult = new TokenClass [] { TokenClass.END_OF_INPUT };

        String sourceCode = " // single line comment  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void manyCommentIgnoringTest() {
        TokenClass [] expectedResult = new TokenClass [] { TokenClass.END_OF_INPUT };

        String sourceCode = " // single line comment // comment \n // comment  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void keywordLexingTest() {
        TokenClass [] expectedResult = new TokenClass [] { 
            TokenClass.KEYWORD_INCLUDE,  TokenClass.KEYWORD_IF,   TokenClass.KEYWORD_ELSE,     TokenClass.KEYWORD_INT,  TokenClass.KEYWORD_STR, 
            TokenClass.KEYWORD_WHILE,    TokenClass.END_OF_INPUT 
        };

        String sourceCode = "include if else int str while ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void identifierLexingTest() {
        TokenClass [] expectedResult = new TokenClass [] { 
            TokenClass.IDENTIFIER,   TokenClass.IDENTIFIER,   TokenClass.IDENTIFIER,   TokenClass.IDENTIFIER,   TokenClass.IDENTIFIER, 
            TokenClass.IDENTIFIER,   TokenClass.IDENTIFIER,   TokenClass.END_OF_INPUT 
        };

        String sourceCode = "a X var_x varOne VAR var1 var_1";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void operatorLexingTest() {
        TokenClass [] expectedResult = new TokenClass  [] { 
            TokenClass.OP_ADD,          TokenClass.OP_SUBTRACT,         TokenClass.OP_BITWISEAND,   TokenClass.OP_DIVIDE,  
            TokenClass.OP_BITWISEXOR,   TokenClass.OP_MOD,              TokenClass.OP_INCREMENTONE, TokenClass.OP_DECREMENTONE, 
            TokenClass.OP_COMPUNDADD,   TokenClass.OP_COMPUNDMULTIPLY,  TokenClass.OP_AND,          TokenClass.OP_OR,
            TokenClass.END_OF_INPUT 
        };

        String sourceCode = "+ - & / ^ % ++ -- += *= && ||";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void integerLiteralLexingTest() {
        TokenClass [] expectedResult = new TokenClass  [] { 
            TokenClass.INTEGER,     TokenClass.INTEGER, TokenClass.INTEGER,     TokenClass.INTEGER, TokenClass.INTEGER,
            TokenClass.OP_NEGATE,   TokenClass.INTEGER, TokenClass.OP_NEGATE,   TokenClass.INTEGER, TokenClass.OP_NEGATE, 
            TokenClass.INTEGER,     TokenClass.END_OF_INPUT
        };

        String sourceCode = "0 000 1 25 999999 -1 -77777 -0";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void realLiteralLexingTest() {
        TokenClass [] expectedResult = new TokenClass  [] { 
            TokenClass.DOUBLE,  TokenClass.DOUBLE,  TokenClass.DOUBLE,  TokenClass.DOUBLE,  TokenClass.OP_NEGATE,
            TokenClass.DOUBLE,  TokenClass.OP_NEGATE, TokenClass.DOUBLE, TokenClass.END_OF_INPUT
        };

        String sourceCode = "0.0 1.123 0.88888 2523123.0 -0.1 -12.123";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void stringLiteralLexingTest() {
        TokenClass [] expectedResult = new TokenClass  [] { 
            TokenClass.STRING,  TokenClass.STRING,  TokenClass.STRING,  TokenClass.STRING,  TokenClass.STRING,
            TokenClass.END_OF_INPUT
        };

        String sourceCode = "\"a\" \"string\" \"001\" \"the space\" \"1.123\"";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void codeStringLexingTest() {        
        TokenClass [] expectedResult = new TokenClass [] { 
            TokenClass.KEYWORD_INCLUDE,  TokenClass.IDENTIFIER,   TokenClass.SEMICOLON,        TokenClass.KEYWORD_INT,      TokenClass.IDENTIFIER, 
            TokenClass.OP_ASSIGN,        TokenClass.DOUBLE,       TokenClass.SEMICOLON,        TokenClass.KEYWORD_STR,      TokenClass.IDENTIFIER, 
            TokenClass.OP_ASSIGN,        TokenClass.STRING,       TokenClass.SEMICOLON,        TokenClass.KEYWORD_FUNCTION, TokenClass.IDENTIFIER, 
            TokenClass.LEFTPAREN,        TokenClass.IDENTIFIER,   TokenClass.COLON,            TokenClass.KEYWORD_INT,      TokenClass.COMMA, 
            TokenClass.IDENTIFIER,       TokenClass.COLON,        TokenClass.KEYWORD_DOUBLE,   TokenClass.RIGHTPAREN,       TokenClass.COLON,
            TokenClass.KEYWORD_DOUBLE,   TokenClass.END_OF_INPUT 
        };

        String sourceCode = "include io; \nint x = 141.5; \n\n str s = \"XYZ\"; \nfunction my_function(a : int, b : double) : double";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void longCodeStringTest() {
        TokenClass [] expectedResult = new TokenClass [] {
            TokenClass.KEYWORD_INCLUDE, TokenClass.IDENTIFIER,      TokenClass.SEMICOLON,       TokenClass.KEYWORD_FUNCTION,
            TokenClass.IDENTIFIER,      TokenClass.LEFTPAREN,       TokenClass.IDENTIFIER,      TokenClass.COLON,
            TokenClass.KEYWORD_INT,     TokenClass.COMMA,           TokenClass.IDENTIFIER,      TokenClass.COLON,
            TokenClass.KEYWORD_DOUBLE,  TokenClass.RIGHTPAREN,      TokenClass.COLON,           TokenClass.KEYWORD_DOUBLE,
            TokenClass.LEFTBRACE,       TokenClass.KEYWORD_INT,     TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,
            TokenClass.INTEGER,         TokenClass.SEMICOLON,       TokenClass.KEYWORD_BOOL,    TokenClass.IDENTIFIER,
            TokenClass.OP_ASSIGN,       TokenClass.KEYWORD_TRUE,    TokenClass.SEMICOLON,       TokenClass.KEYWORD_WHILE,
            TokenClass.LEFTPAREN,       TokenClass.IDENTIFIER,      TokenClass.OP_EQUAL,        TokenClass.KEYWORD_TRUE,
            TokenClass.RIGHTPAREN,      TokenClass.LEFTBRACE,       TokenClass.KEYWORD_IF,      TokenClass.LEFTPAREN,
            TokenClass.IDENTIFIER,      TokenClass.OP_GREATER,      TokenClass.IDENTIFIER,      TokenClass.RIGHTPAREN,
            TokenClass.LEFTBRACE,       TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,       TokenClass.KEYWORD_FALSE,
            TokenClass.SEMICOLON,       TokenClass.RIGHTBRACE,      TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,
            TokenClass.IDENTIFIER,      TokenClass.OP_ADD,          TokenClass.INTEGER,         TokenClass.SEMICOLON,
            TokenClass.RIGHTBRACE,      TokenClass.KEYWORD_RETURN,  TokenClass.IDENTIFIER,      TokenClass.OP_MULTIPLY,
            TokenClass.IDENTIFIER,      TokenClass.SEMICOLON,       TokenClass.RIGHTBRACE,      TokenClass.KEYWORD_FUNCTION,
            TokenClass.IDENTIFIER,      TokenClass.LEFTPAREN,       TokenClass.RIGHTPAREN,      TokenClass.COLON,
            TokenClass.KEYWORD_INT,     TokenClass.LEFTBRACE,       TokenClass.IDENTIFIER,      TokenClass.LEFTPAREN,
            TokenClass.STRING,          TokenClass.RIGHTPAREN,      TokenClass.SEMICOLON,       TokenClass.KEYWORD_INT,
            TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,       TokenClass.INTEGER,         TokenClass.SEMICOLON,
            TokenClass.KEYWORD_DOUBLE,  TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,       TokenClass.DOUBLE,
            TokenClass.SEMICOLON,       TokenClass.KEYWORD_DOUBLE,  TokenClass.IDENTIFIER,      TokenClass.OP_ASSIGN,
            TokenClass.IDENTIFIER,      TokenClass.LEFTPAREN,       TokenClass.IDENTIFIER,      TokenClass.COMMA,
            TokenClass.IDENTIFIER,      TokenClass.RIGHTPAREN,      TokenClass.SEMICOLON,       TokenClass.IDENTIFIER,
            TokenClass.LEFTPAREN,       TokenClass.STRING,          TokenClass.COMMA,           TokenClass.IDENTIFIER,
            TokenClass.RIGHTPAREN,      TokenClass.SEMICOLON,       TokenClass.KEYWORD_RETURN,  TokenClass.INTEGER,
            TokenClass.SEMICOLON,       TokenClass.RIGHTBRACE,      TokenClass.END_OF_INPUT
        };

        String sourceCode = 
            "include io;\n"
            + "\n"
            + "// custom function\n"
            + "function my_function(a : int, b : double) : double {\n"
            + "    int idx = 0;\n"
            + "    bool loop = true;\n"
            + "\n"
            + "    while (loop == true) {\n"
            + "        if (idx > b) {\n"
            + "            loop = false;\n"
            + "        }\n"
            + "\n"
            + "        idx = idx + 1;\n"
            + "    }\n"
            + "\n"
            + "    return a * b;\n"
            + "}\n"
            + "\n"
            + "// main function\n"
            + "function main() : int {\n"
            + "    print(\"Hello World!\");\n"
            + "\n"
            + "    int a = 5;\n"
            + "    double b = 2.3;\n"
            + "\n"
            + "    double r = my_fuction(a, b);\n"
            + "    print(\"Result = \", r);\n"
            + "\n"
            + "    return 1;\n"
            + "}\n";
        
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass(), expectedResult[i]);
            i++;
        }
    }
}