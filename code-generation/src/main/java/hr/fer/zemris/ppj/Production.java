package hr.fer.zemris.ppj;

import java.util.Arrays;
import java.util.Collection;

/**
 * <code>Productions</code> is a enumeration of productions for the ppjC language.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum Production {
    PRIMARY_EXPRESSION_1("<primarni_izraz> ::= IDN"),
    PRIMARY_EXPRESSION_2("<primarni_izraz> ::= BROJ"),
    PRIMARY_EXPRESSION_3("<primarni_izraz> ::= ZNAK"),
    PRIMARY_EXPRESSION_4("<primarni_izraz> ::= NIZ_ZNAKOVA"),
    PRIMARY_EXPRESSION_5("<primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA"),
    POSTFIX_EXPRESSION_1("<postfiks_izraz> ::= <primarni_izraz>"),
    POSTFIX_EXPRESSION_2("<postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA"),
    POSTFIX_EXPRESSION_3("<postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA"),
    POSTFIX_EXPRESSION_4("<postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA"),
    POSTFIX_EXPRESSION_5("<postfiks_izraz> ::= <postfiks_izraz> OP_INC"),
    POSTFIX_EXPRESSION_6("<postfiks_izraz> ::= <postfiks_izraz> OP_DEC"),
    ARGUMENT_LIST_1("<lista_argumenata> ::= <izraz_pridruzivanja>"),
    ARGUMENT_LIST_2("<lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>"),
    UNARY_EXPRESSION_1("<unarni_izraz> ::= <postfiks_izraz>"),
    UNARY_EXPRESSION_2("<unarni_izraz> ::= OP_INC <unarni_izraz>"),
    UNARY_EXPRESSION_3("<unarni_izraz> ::= OP_DEC <unarni_izraz>"),
    UNARY_EXPRESSION_4("<unarni_izraz> ::= <unarni_operator> <cast_izraz>"),
    UNARY_OPERATOR_1("<unarni_operator> ::= PLUS"),
    UNARY_OPERATOR_2("<unarni_operator> ::= MINUS"),
    UNARY_OPERATOR_3("<unarni_operator> ::= OP_TILDA"),
    UNARY_OPERATOR_4("<unarni_operator> ::= OP_NEG"),
    CAST_EXPRESSION_1("<cast_izraz> ::= <unarni_izraz>"),
    CAST_EXPRESSION_2("<cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>"),
    TYPE_NAME_1("<ime_tipa> ::= <specifikator_tipa>"),
    TYPE_NAME_2("<ime_tipa> ::= KR_CONST <specifikator_tipa>"),
    TYPE_SPECIFIER_1("<specifikator_tipa> ::= KR_VOID"),
    TYPE_SPECIFIER_2("<specifikator_tipa> ::= KR_CHAR"),
    TYPE_SPECIFIER_3("<specifikator_tipa> ::= KR_INT"),
    MULTIPLICATIVE_EXPRESSION_1("<multiplikativni_izraz> ::= <cast_izraz>"),
    MULTIPLICATIVE_EXPRESSION_2("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>"),
    MULTIPLICATIVE_EXPRESSION_3("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI <cast_izraz>"),
    MULTIPLICATIVE_EXPRESSION_4("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>"),
    ADDITIVE_EXPRESSION_1("<aditivni_izraz> ::= <multiplikativni_izraz>"),
    ADDITIVE_EXPRESSION_2("<aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>"),
    ADDITIVE_EXPRESSION_3("<aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>"),
    RELATIONAL_EXPRESSION_1("<odnosni_izraz> ::= <aditivni_izraz>"),
    RELATIONAL_EXPRESSION_2("<odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>"),
    RELATIONAL_EXPRESSION_3("<odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>"),
    RELATIONAL_EXPRESSION_4("<odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>"),
    RELATIONAL_EXPRESSION_5("<odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>"),
    EQUALITY_EXPRESSION_1("<jednakosni_izraz> ::= <odnosni_izraz>"),
    EQUALITY_EXPRESSION_2("<jednakosni_izraz> ::= <jednakosni_izraz> OP_EQ <odnosni_izraz>"),
    EQUALITY_EXPRESSION_3("<jednakosni_izraz> ::= <jednakosni_izraz> OP_NEQ <odnosni_izraz>"),
    BINARY_AND_EXPRESSION_1("<bin_i_izraz> ::= <jednakosni_izraz>"),
    BINARY_AND_EXPRESSION_2("<bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>"),
    BINARY_XOR_EXPRESSION_1("<bin_xili_izraz> ::= <bin_i_izraz>"),
    BINARY_XOR_EXPRESSION_2("<bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>"),
    BINARY_OR_EXPRESSION_1("<bin_ili_izraz> ::= <bin_xili_izraz>"),
    BINARY_OR_EXPRESSION_2("<bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>"),
    LOGICAL_AND_EXPRESSION_1("<log_i_izraz> ::= <bin_ili_izraz>"),
    LOGICAL_AND_EXPRESSION_2("<log_i_izraz> ::= <log_i_izraz> OP_I <bin_ili_izraz>"),
    LOGICAL_OR_EXPRESSION_1("<log_ili_izraz> ::= <log_i_izraz>"),
    LOGICAL_OR_EXPRESSION_2("<log_ili_izraz> ::= <log_ili_izraz> OP_ILI <log_i_izraz>"),
    ASSIGN_EXPRESSION_1("<izraz_pridruzivanja> ::= <log_ili_izraz>"),
    ASSIGN_EXPRESSION_2("<izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>"),
    EXPRESSION_1("<izraz> ::= <izraz_pridruzivanja>"),
    EXPRESSION_2("<izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>"),
    COMPOUND_INSTRUCTION_1("<slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA"),
    COMPOUND_INSTRUCTION_2("<slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA"),
    INSTRUCTION_LIST_1("<lista_naredbi> ::= <naredba>"),
    INSTRUCTION_LIST_2("<lista_naredbi> ::= <lista_naredbi> <naredba>"),
    INSTRUCTION_1("<naredba> ::= <slozena_naredba>"),
    INSTRUCTION_2("<naredba> ::= <izraz_naredba>"),
    INSTRUCTION_3("<naredba> ::= <naredba_grananja>"),
    INSTRUCTION_4("<naredba> ::= <naredba_petlje>"),
    INSTRUCTION_5("<naredba> ::= <naredba_skoka>"),
    EXPRESSION_INSTRUCTION_1("<izraz_naredba> ::= TOCKAZAREZ"),
    EXPRESSION_INSTRUCTION_2("<izraz_naredba> ::= <izraz> TOCKAZAREZ"),
    BRANCH_INSTRUCTION_1("<naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>"),
    BRANCH_INSTRUCTION_2("<naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>"),
    LOOP_INSTRUCTION_1("<naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>"),
    LOOP_INSTRUCTION_2("<naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>"),
    LOOP_INSTRUCTION_3(
            "<naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>"),
    JUMP_INSTRUCTION_1("<naredba_skoka> ::= KR_CONTINUE TOCKAZAREZ"),
    JUMP_INSTRUCTION_2("<naredba_skoka> ::= KR_BREAK TOCKAZAREZ"),
    JUMP_INSTRUCTION_3("<naredba_skoka> ::= KR_RETURN TOCKAZAREZ"),
    JUMP_INSTRUCTION_4("<naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ"),

    TRANSLATION_UNIT_1("<prijevodna_jedinica> ::= <vanjska_deklaracija>"),
    TRANSLATION_UNIT_2("<prijevodna_jedinica> ::= <prijevodna_jedinica> <vanjska_deklaracija>"),
    OUTER_DECLARATION_1("<vanjska_deklaracija> ::= <definicija_funkcije>"),
    OUTER_DECLARATION_2("<vanjska_deklaracija> ::= <deklaracija>"),
    FUNCTION_DEFINITION_1("<definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>"),
    FUNCTION_DEFINITION_2(
            "<definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA <slozena_naredba>"),
    PARAMETER_LIST_1("<lista_parametara> ::= <deklaracija_parametra>"),
    PARAMETER_LIST_2("<lista_parametara> ::= <lista_parametara> ZAREZ <deklaracija_parametra>"),
    PARAMETER_DECLARATION_1("<deklaracija_parametra> ::= <ime_tipa> IDN"),
    PARAMETER_DECLARATION_2("<deklaracija_parametra> ::= <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA"),
    DECLARATION_LIST_1("<lista_deklaracija> ::= <deklaracija>"),
    DECLARATION_LIST_2("<lista_deklaracija> ::= <lista_deklaracija> <deklaracija>"),
    DECLARATION_1("<deklaracija> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ"),
    DECLARATOR_INITIALIZATION_LIST_1("<lista_init_deklaratora> ::= <init_deklarator>"),
    DECLARATOR_INITIALIZATION_LIST_2("<lista_init_deklaratora> ::= <lista_init_deklaratora> ZAREZ <init_deklarator>"),
    INITIALIZATION_DECLARATOR_1("<init_deklarator> ::= <izravni_deklarator>"),
    INITIALIZATION_DECLARATOR_2("<init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>"),
    DIRECT_DECLARATOR_1("<izravni_deklarator> ::= IDN"),
    DIRECT_DECLARATOR_2("<izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA"),
    DIRECT_DECLARATOR_3("<izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA"),
    DIRECT_DECLARATOR_4("<izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA"),
    INITIALIZATOR_1("<inicijalizator> ::= <izraz_pridruzivanja>"),
    INITIALIZATOR_2("<inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA"),
    ASSIGN_EXPRESSION_LIST_1("<lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>"),
    ASSIGN_EXPRESSION_LIST_2(
            "<lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>");

    private final String production;
    private static final Collection<Production> values = Arrays.asList(values());

    Production(String production) {
        this.production = production;
    }

    public static Production fromNode(final Node node) {
        final String production = node.production();
        for (Production value : values) {
            if (value.production.equals(production)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No production!");
    }
}
