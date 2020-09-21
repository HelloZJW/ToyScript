import {SimpleLexer, SimpleToken} from "./SimpleLexer";
import {TokenType} from "./TokenType";

enum ASTNodeType {
    Program = 'Program',
    IntDeclaration = 'IntDeclaration',
    AssignmentExp = 'AssignmentExp',
    Additive = 'Additive',
    Multiplicative = 'Multiplicative',
    IntLiteral = 'IntLiteral'
}

class SimpleASTNode {
    type: ASTNodeType;
    value: string;
    children: SimpleASTNode[] = [];

    constructor(type, value) {
        this.type = type;
        this.value = value;
    }
}

class SimpleCalculator {
    constructor() {
    }

    /**
     *
     * 产生式：intDeclaration : Int Identifier ('=' additiveExpression)?;
     //伪代码
     MatchIntDeclare(){
            MatchToken(Int)；        //匹配Int关键字
            MatchIdentifier();       //匹配标识符
            MatchToken(equal);       //匹配等号
            MatchExpression();       //匹配表达式
        }
     * @constructor
     */
    IntDeclare() {
        let lexer = new SimpleLexer();
        let tokens = lexer.tokenize('int age = 45');
        let token = tokens[0];
        if (token.type !== TokenType.Int) {
            throw new Error('第一个 token 必须是 Int 关键字')
        }

        tokens.shift(); // 消耗掉 Int
        token = tokens[0];
        if (token.type === TokenType.Identifier) {
            token = tokens.shift();
            let node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.text);
            token = tokens[0];
            if (token && token.type === TokenType.Assignment) {
                tokens.shift(); // 消耗掉 =
                let child = this.additive(tokens); //匹配一个表达式
                if (!child) {
                    throw new Error('变量初始化失败，需要一个表达式');
                } else {
                    node.children.push(child);
                    console.log(node);
                }
            }

        } else {
            throw new Error('变量名 expected')
        }
    }

    evaluate(script: string) {
        let rootNode = this.parse(script);
        this.dumpAST(rootNode, '');
        this.evaluateHelper(rootNode, '');
    }

    private evaluateHelper(node: SimpleASTNode, indent: string) {
        let result = 0;
        console.log(indent + "Calculating: " + node.type);
        switch (node.type) {
            case ASTNodeType.Program:
                for (let child in node.children) {
                    result = this.evaluateHelper(node.children[0], indent + '\t')
                }
                break;
            case ASTNodeType.Additive: {
                let child1 = node.children[0];
                let child2 = node.children[1];
                let value1 = this.evaluateHelper(child1, indent + '\t');
                let value2 = this.evaluateHelper(child2, indent + '\t');

                if (node.value === '+') {
                    result = value1 + value2;
                } else {
                    result = value1 - value2;
                }
                break;
            }
            case ASTNodeType.Multiplicative: {
                let child1 = node.children[0];
                let child2 = node.children[1];
                let value1 = this.evaluateHelper(child1, indent + '\t');
                let value2 = this.evaluateHelper(child2, indent + '\t');

                if (node.value === '*') {
                    result = value1 * value2;
                } else {
                    result = value1 / value2;
                }
                break;
            }

            case ASTNodeType.IntLiteral:
                result = Number(node.value);
                break;
        }

        console.log(indent + 'Result:' + result);
        return result;
    }

    parse(script: string) {
        let lexer = new SimpleLexer();
        let tokens = lexer.tokenize(script);

        return this.prog(tokens);
    }

    prog(tokens: SimpleToken[]) {
        let node = new SimpleASTNode(ASTNodeType.Program, 'Calculator');
        let child = this.additive(tokens);
        if (child) node.children.push(child);

        return node;
    }

// 产生式：左递归问题，死循环
// additiveExpression
//     :   multiplicativeExpression
// |   additiveExpression Plus multiplicativeExpression
// ;
//

// 产生式：交换位置，不会死循环，但是会有优先级问题
// additiveExpression
//     :   multiplicativeExpression
// |   multiplicativeExpression Plus additiveExpression
// ;
    additive(tokens: SimpleToken[]): SimpleASTNode {
        let child1 = this.multiplicative(tokens);  // 计算第一个子节点
        let node = child1;
        let token = tokens[0];
        if (child1 != null && token != null) {
            if (token.type === TokenType.Plus || token.type === TokenType.Minus) {
                token = tokens.shift();
                // 加法表达式
                let child2 = this.additive(tokens);
                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Additive, token.text);
                    node.children.push(child1);
                    node.children.push(child2);
                } else {
                    throw new Error("invalid additive expression, expecting the right part.");
                }
            }
        }

        return node
    }

// multiplicativeExpression
//     :   IntLiteral
// |   multiplicativeExpression Star IntLiteral
// ;
    multiplicative(tokens: SimpleToken[]) {
        let child1: SimpleASTNode = null;
        let token = tokens[0];
        if (token != null) {
            if (token.type === TokenType.IntLiteral) {
                token = tokens.shift();
                child1 = new SimpleASTNode(ASTNodeType.IntLiteral, token.text);
            }
        }

        let node = child1;

        token = tokens[0];
        // node 为空 代表没有匹配 IntLiteral
        if (child1 != null && token != null) {
            if (token.type === TokenType.Star || token.type === TokenType.Slash) {
                token = tokens.shift();
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


    private dumpAST(node: SimpleASTNode, indent: string) {
        console.log(indent + node.type + " " + node.value);
        for (let child of node.children) {
            this.dumpAST(child, indent + "\t");
        }
    }
}


let calculator = new SimpleCalculator();
calculator.evaluate('2+3*5');