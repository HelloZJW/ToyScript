// Generated from toy/ToyScript.g4 by ANTLR 4.8

package main.toy;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ToyScriptParser}.
 */
public interface ToyScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(ToyScriptParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(ToyScriptParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(ToyScriptParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(ToyScriptParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(ToyScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(ToyScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(ToyScriptParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(ToyScriptParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(ToyScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(ToyScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(ToyScriptParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(ToyScriptParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(ToyScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(ToyScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(ToyScriptParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(ToyScriptParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(ToyScriptParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(ToyScriptParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(ToyScriptParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(ToyScriptParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(ToyScriptParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(ToyScriptParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(ToyScriptParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(ToyScriptParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(ToyScriptParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(ToyScriptParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(ToyScriptParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(ToyScriptParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(ToyScriptParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(ToyScriptParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(ToyScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(ToyScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(ToyScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(ToyScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(ToyScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(ToyScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(ToyScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(ToyScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(ToyScriptParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(ToyScriptParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(ToyScriptParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(ToyScriptParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(ToyScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(ToyScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(ToyScriptParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(ToyScriptParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ToyScriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ToyScriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(ToyScriptParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(ToyScriptParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(ToyScriptParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(ToyScriptParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ToyScriptParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ToyScriptParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ToyScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ToyScriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatements(ToyScriptParser.BlockStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatements(ToyScriptParser.BlockStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(ToyScriptParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(ToyScriptParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ToyScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ToyScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(ToyScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(ToyScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(ToyScriptParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(ToyScriptParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(ToyScriptParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(ToyScriptParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(ToyScriptParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(ToyScriptParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForControl(ToyScriptParser.EnhancedForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForControl(ToyScriptParser.EnhancedForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(ToyScriptParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(ToyScriptParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ToyScriptParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ToyScriptParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(ToyScriptParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(ToyScriptParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ToyScriptParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ToyScriptParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ToyScriptParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ToyScriptParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(ToyScriptParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(ToyScriptParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(ToyScriptParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(ToyScriptParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(ToyScriptParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(ToyScriptParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(ToyScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(ToyScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(ToyScriptParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(ToyScriptParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(ToyScriptParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(ToyScriptParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ToyScriptParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ToyScriptParser.ArgumentsContext ctx);
}