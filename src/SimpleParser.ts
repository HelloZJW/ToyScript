import {TokenReader} from "./TokenReader";
import {TokenType} from "./TokenType";
import {SimpleLexer} from "./SimpleLexer";

export enum ASTNodeType {
    Program = 'Program',
    IntDeclaration = 'IntDeclaration',
    Identifier = 'Identifier',
    AssignmentExp = 'AssignmentExp',
    Additive = 'Additive',
    Multiplicative = 'Multiplicative',
    IntLiteral = 'IntLiteral',
}

export class SimpleASTNode {
    type: ASTNodeType;
    value: string;
    children: SimpleASTNode[] = [];

    constructor(type, value) {
        this.type = type;
        this.value = value;
    }
}

export class SimpleParser {
    constructor() {
    }

    /**
     * 将脚本解析为 tokens， 并把 tokens 解析为 AST
     * @param script
     */
    parse(script: string): SimpleASTNode {
        let lexer = new SimpleLexer();
        let tokens = lexer.tokenize(script);

        return this.program(tokens);
    }

    /**
     * 格式化打印树形 Node
     * @param node
     * @param indent
     */
    public dumpAST(node: SimpleASTNode, indent: string) {
        console.log(indent + node.type + " " + node.value);
        for (let child of node.children) {
            this.dumpAST(child, indent + "\t");
        }
    }

    /**
     * 构建程序 AST
     * @param tokens
     */
    private program(tokens: TokenReader): SimpleASTNode {
        let node = new SimpleASTNode(ASTNodeType.Program, 'Calculator');
        while (tokens.peek() != null) {
            let child = this.intDeclare(tokens);

            if (child == null) {
                child = this.expressionStatement(tokens);
            }

            if (child == null) {
                child = this.assignmentStatement(tokens);
            }
            if (child) {
                node.children.push(child);
            } else {
                throw new Error("unknown statement");
            }
        }

        return node;
    }

    /**
     * 产生式：intDeclaration : Int Identifier ('=' additiveExpression)?;
     * 整型变量声明，如：
     * int a;
     * int b = 2*3;
     */
    private intDeclare(tokens: TokenReader) {
        let node: SimpleASTNode;
        let token = tokens.peek();
        if (token != null && token.type == TokenType.Int) {
            tokens.read(); // 消耗掉 Int
            token = tokens.peek();
            if (token.type === TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.text);
                token = tokens.peek();
                if (token && token.type === TokenType.Assignment) {
                    tokens.read(); // 消耗掉 =
                    let child = this.additive(tokens); //匹配一个表达式
                    if (child == null) {
                        throw new Error("invalide variable initialization, expecting an expression");
                    } else {
                        node.children.push(child);
                    }
                }
            } else {
                throw new Error('variable name expected');
            }
        }

        if (node != null) {
            token = tokens.peek();
            if (token != null && token.type == TokenType.SemiColon) {
                tokens.read();
            } else {
                throw new Error("invalid statement, expecting semicolon");
            }
        }

        return node;
    }


    /**
     * 表达式，目前只支持加法表达式
     * expressionStatement : additiveExpression ';';
     */
    private expressionStatement(tokens: TokenReader) {
        let pos = tokens.position;
        let node = this.additive(tokens);
        if (node != null) {
            let token = tokens.peek();
            if (token != null && token.type === TokenType.SemiColon) {
                tokens.read();
            } else {
                node = null;
                tokens.position = pos; // 回溯；
            }
        }

        return node;
    }

    /**
     * assignmentStatement : Identifier '=' additiveExpression ';';
     * @param tokens
     */
    private assignmentStatement(tokens: TokenReader) {
        let node;
        let token = tokens.peek();
        if (token != null) {
            if (token.type === TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.AssignmentExp, token.text);
                token = tokens.peek();  // 看看下一个是否等号
                if (token.type === TokenType.Assignment) {
                    tokens.read();
                    let child = this.additive(tokens);
                    if (child != null) {
                        node.children.push(child);
                        token = tokens.peek();
                        if (token.type === TokenType.SemiColon) {
                            tokens.read();
                        } else {
                            throw new Error('invalid statement, expecting semicolon');
                        }
                    } else {
                        throw new Error('invalid statement, expecting semicolon');
                    }
                } else {
                    // 回溯，吐出之前消化掉的标识符；
                    tokens.unread();
                    node = null;
                }
            }
        }

        return node;
    }

// 产生式：左递归问题，死循环
// additiveExpression
// :   multiplicativeExpression
// |   additiveExpression Plus multiplicativeExpression
// ;
//

// 产生式：交换位置，不会死循环，但是会有结合性问题
// additiveExpression
// :   multiplicativeExpression
// |   multiplicativeExpression Plus additiveExpression
// ;

    /**
     * 匹配 additiveExpression
     * @param tokens
     */
    private additive(tokens: TokenReader): SimpleASTNode {
        // 匹配 multiplicativeExpression
        let child1 = this.multiplicative(tokens);  // 计算第一个子节点
        let node = child1;
        // let token = tokens.peek();
        // if (child1 != null && token != null) {
        //     // 如果表达式没有结束，并且 下一个 Token 是加/减法法，匹配嵌套的 additiveExpression
        //     if (token.type === TokenType.Plus || token.type === TokenType.Minus) {
        //         // 处理 multiplicativeExpression Plus additiveExpression 产生式
        //         token = tokens.read();
        //         // 加法表达式
        //         let child2 = this.additive(tokens);
        //         if (child2 != null) {
        //             node = new SimpleASTNode(ASTNodeType.Additive, token.text);
        //             node.children.push(child1);
        //             node.children.push(child2);
        //         } else {
        //             throw new Error("invalid additive expression, expecting the right part.");
        //         }
        //     }
        // }

        if (child1 != null) {
            while (true) {
                let token = tokens.peek();
                if (token != null && (token.type == TokenType.Plus || token.type == TokenType.Minus)) {
                    token = tokens.read();
                    let child2 = this.multiplicative(tokens);
                    node = new SimpleASTNode(ASTNodeType.Additive, token?.text);
                    node.children.push(child1);
                    node.children.push(child2);
                    child1 = node;
                } else {
                    break;
                }
            }
        }

        return node
    }

// 左递归问题
// multiplicativeExpression
// :   IntLiteral
// |   multiplicativeExpression Star IntLiteral
// ;

// 交换位置，防止左递归的问题。
// multiplicativeExpression
// :   IntLiteral
// |   IntLiteral Star multiplicativeExpression
// ;

    /**
     * 匹配 multiplicativeExpression
     * @param tokens
     */
    private multiplicative(tokens: TokenReader) {
        let child1: SimpleASTNode = this.primary(tokens);
        let node = child1;

        let token = tokens.peek();
        // node 为空 代表没有匹配 IntLiteral
        if (child1 != null && token != null) {
            // 匹配是否有嵌套的 multiplicativeExpression
            if (token.type === TokenType.Star || token.type === TokenType.Slash) {
                token = tokens.read();
                let child2 = this.multiplicative(tokens);
                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.text);
                    node.children.push(child1);
                    node.children.push(child2);
                } else {
                    throw new Error("invalid multiplicative expression, expecting the right part.");
                }
            }
        }

        return node;
    }

    /**
     * 基础表达式的定义，变量、字面量、或者在括号内的表达式，提升括号中的表达式的优先级
     * primaryExpression : Identifier | IntLiteral | '(' additiveExpression ')';
     */
    private primary(tokens: TokenReader) {
        let node;
        let token = tokens.peek();
        if (token != null) {
            if (token.type === TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.Identifier, token.text);
            } else if (token.type === TokenType.IntLiteral) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.text);
            } else if (token.type === TokenType.LeftParentheses) {
                tokens.read();
                node = this.additive(tokens);
                if (node != null) {
                    token = token[0];
                    if (token != null && token.type === TokenType.RightParenthese) {
                        tokens.read();
                    } else {
                        throw new Error("expecting right parentheses");
                    }
                } else {
                    throw new Error("expecting an additive expression inside parentheses");
                }
            }
        }

        return node;
    }
}

// let parser = new SimpleParser();
// let script = 'int age = 45+2; age= 20; age+10*2;';
// let tree = parser.parse(script);
// parser.dumpAST(tree, "");
//
// script = "2+3+;";
// tree = parser.parse(script);
// parser.dumpAST(tree, "");
//
// script = "2+3*;";
// tree = parser.parse(script);
// parser.dumpAST(tree, "");