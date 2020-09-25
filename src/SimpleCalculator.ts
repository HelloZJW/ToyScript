import {ASTNodeType, SimpleASTNode, SimpleParser} from "./SimpleParser";

class SimpleCalculator {
    constructor() {
    }

    /**
     * 计算入口
     * @param script 源码
     */
    evaluate(script: string) {
        let parser = new SimpleParser();
        let rootNode = parser.parse(script);
        parser.dumpAST(rootNode, '');
        this.evaluateHelper(rootNode, '');
    }

    /**
     * 计算实际的执行函数
     * 根据 AST 四则运算的结果
     * @param node
     * @param indent
     */
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
}


let calculator = new SimpleCalculator();
calculator.evaluate('2+3*5;');