package com.lexer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LexerTest {

    @Test
    public void dummyTest() {
        assertEquals(true, true);
        // try {
        //     String sourceCode = new String (Files.readAllBytes(Paths.get("C:\\Users\\nalaka\\Downloads\\Intel\\pcompiler\\test.pll")));
        //     Lexer lexer = new Lexer(sourceCode);
        //     ArrayList<Token> tokens = lexer.tokenize();

        //     for (Token token : tokens) {
        //         System.out.println(token.getTokenClass().name() + "->" + token.getValue());
        //     }
        // } catch (IOException e) {
        //     System.out.println(e);
        // }
    }

    @Test
    public void keywordLexingTest() {
        String [] expectedResult = new String [] { 
            "KEYWORD_INCLUDE",  "KEYWORD_IF",   "KEYWORD_ELSE",     "KEYWORD_INT",  "KEYWORD_STR", 
            "KEYWORD_WHILE",    "END_OF_INPUT" 
        };

        String sourceCode = "include if else int str while ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void singleLineBlanksIgnoringTest() {
        String [] expectedResult = new String [] { "END_OF_INPUT" };

        String sourceCode = "             \t  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void manyLineBlanksIgnoringTest() {
        String [] expectedResult = new String [] { "END_OF_INPUT" };

        String sourceCode = "           \n  \t \n\n  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void onlyCommentIgnoringTest() {
        String [] expectedResult = new String [] { "END_OF_INPUT" };

        String sourceCode = " // single line comment  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void manyCommentIgnoringTest() {
        String [] expectedResult = new String [] { "END_OF_INPUT" };

        String sourceCode = " // single line comment // comment \n // comment  ";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }

    @Test
    public void codeStringLexingTest() {        
        String [] expectedResult = new String [] { 
            "KEYWORD_INCLUDE",  "IDENTIFIER",   "SEMICOLON",        "KEYWORD_INT",      "IDENTIFIER", 
            "OP_ASSIGN",        "DOUBLE",       "SEMICOLON",        "KEYWORD_STR",      "IDENTIFIER", 
            "OP_ASSIGN",        "STRING",       "SEMICOLON",        "KEYWORD_FUNCTION", "IDENTIFIER", 
            "LEFTPAREN",        "IDENTIFIER",   "COLON",            "KEYWORD_INT",      "COMMA", 
            "IDENTIFIER",       "COLON",        "KEYWORD_DOUBLE",   "RIGHTPAREN",       "COLON",
            "KEYWORD_DOUBLE",   "END_OF_INPUT" 
        };

        String sourceCode = "include io; \nint x = 141.5; \n\n str s = \"XYZ\"; \nfunction my_function(a : int, b : double) : double";
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Token> tokens = lexer.tokenize();

        int i = 0;
        for (Token token : tokens) {
            assertEquals(token.getTokenClass().name(), expectedResult[i]);
            i++;
        }
    }
}