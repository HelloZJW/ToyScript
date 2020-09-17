import {SimpleLexer, SimpleToken} from "./SimpleLexer";
import {TokenType} from "./TokenType";

enum ASTNodeType {
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
                if (!child){
                    throw new Error('变量初始化失败，需要一个表达式');
                }else {
                    node.children.push(child);
                    console.log(node);
                }
            }

        } else {
            throw new Error('变量名 expected')
        }
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
        if (child1 != null && token != null){
            if (token.type === TokenType.Plus || token.type === TokenType.Minus){
                token = tokens.shift();
                // 加法表达式
                let child2 = this.additive(tokens);
                if(child2 != null){
                    node = new SimpleASTNode(ASTNodeType.Additive, token.text);
                    node.children.push(child1);
                    node.children.push(child2);
                }else {
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
    multiplicative(tokens: SimpleToken[]){
        let child1: SimpleASTNode = null;
        let token = tokens[0];
        if (token != null){
            if (token.type === TokenType.IntLiteral){
                token = tokens.shift();
                child1 = new SimpleASTNode(ASTNodeType.IntLiteral, token.text);
            }
        }

        let node = child1;

        token = tokens[0];
        // node 为空 代表没有匹配 IntLiteral
        if (child1 != null && token != null){
            if (token.type === TokenType.Star || token.type === TokenType.Slash){
                token = tokens.shift();
                let child2 = this.multiplicative(tokens);
                if (child2 != null){
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.text);
                    node.children.push(child1);
                    node.children.push(child2);
                }else {
                    throw new Error("invalid multiplicative expression, expecting the right part.");
                }
            }
        }

        return node;
    }
}

// new SimpleCalculator().IntDeclare();

let lexer = new SimpleLexer();
let tokens = lexer.tokenize('2+3*5');

let node = new SimpleCalculator().additive(tokens.concat());
console.log(node);