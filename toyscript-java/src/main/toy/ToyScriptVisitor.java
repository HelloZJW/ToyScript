// Generated from toy/ToyScript.g4 by ANTLR 4.8

package main.toy;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ToyScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ToyScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(ToyScriptParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(ToyScriptParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(ToyScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(ToyScriptParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ToyScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(ToyScriptParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTypeOrVoid(ToyScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(ToyScriptParser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(ToyScriptParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(ToyScriptParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(ToyScriptParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(ToyScriptParser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(ToyScriptParser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(ToyScriptParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ToyScriptParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ToyScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarators(ToyScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(ToyScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(ToyScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(ToyScriptParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(ToyScriptParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(ToyScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(ToyScriptParser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ToyScriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(ToyScriptParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(ToyScriptParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ToyScriptParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ToyScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatements(ToyScriptParser.BlockStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(ToyScriptParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ToyScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(ToyScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(ToyScriptParser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(ToyScriptParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(ToyScriptParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForControl(ToyScriptParser.EnhancedForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(ToyScriptParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(ToyScriptParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(ToyScriptParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ToyScriptParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ToyScriptParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(ToyScriptParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeType(ToyScriptParser.TypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#functionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(ToyScriptParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(ToyScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(ToyScriptParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffix(ToyScriptParser.SuperSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyScriptParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ToyScriptParser.ArgumentsContext ctx);
}