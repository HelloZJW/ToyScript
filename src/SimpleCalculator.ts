import {SimpleLexer, SimpleToken} from "./SimpleLexer";
import {TokenType} from "./TokenType";

enum ASTNodeType {
    IntDeclaration = 'IntDeclaration',
    AssignmentExp = 'AssignmentExp',
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

    additive(tokens: SimpleToken[]): SimpleASTNode {
        console.log(tokens);
        let node: SimpleASTNode;
        node = new SimpleASTNode(ASTNodeType.IntDeclaration, 'ssss');
        return node
    }
}


new SimpleCalculator().IntDeclare();