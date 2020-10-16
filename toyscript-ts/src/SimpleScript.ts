import {ASTNodeType, SimpleASTNode, SimpleParser} from "./SimpleParser";

let readline = require("readline");
let rl = readline.createInterface(process.stdin, process.stdout);

rl.setPrompt("> ");
rl.prompt();

let verbose = process.argv[2];
let variables = new Map();
let parser = new SimpleParser();

rl.on("line", function (line: string) {
    let scriptText = line.trim();
    if (line == "exit") {
        console.log("bye bye!");
        process.exit(0);
    }
    try {
        let tree = parser.parse(scriptText);
        if (verbose) {
            parser.dumpAST(tree, "");
        }
        evaluate(tree, "");
    } catch (e) {
        console.log(e);
    }

    rl.prompt();
});

/**
 * 计算实际的执行函数
 * 根据 AST 四则运算的结果
 * @param node
 * @param indent
 */
function evaluate(node: SimpleASTNode, indent: string) {
    let result = 0;
    if (verbose) {
        console.log(indent + "Calculating: " + node.type);
    }

    switch (node.type) {
        case ASTNodeType.Program: {
            for (let child of node.children) {
                result = evaluate(child, indent);
            }
            break;
        }
        case ASTNodeType.Additive: {
            let child1 = node.children[0];
            let child2 = node.children[1];
            let value1 = evaluate(child1, indent + "\t");
            let value2 = evaluate(child2, indent + "\t");

            if (node.value === "+") {
                result = value1 + value2;
            } else {
                result = value1 - value2;
            }
            break;
        }
        case ASTNodeType.Multiplicative: {
            let child1 = node.children[0];
            let child2 = node.children[1];
            let value1 = evaluate(child1, indent + "\t");
            let value2 = evaluate(child2, indent + "\t");

            if (node.value === "*") {
                result = value1 * value2;
            } else {
                result = value1 / value2;
            }
            break;
        }

        case ASTNodeType.IntLiteral: {
            result = Number(node.value);
            break;
        }
        case ASTNodeType.Identifier: {
            let varName = node.value;
            if (variables.has(varName)) {
                let value = variables.get(varName);
                if (value != null) {
                    result = value;
                } else {
                    throw new Error(
                        "variable " + varName + " has not been set any value"
                    );
                }
            } else {
                throw new Error("unknown variable: " + varName);
            }
            break;
        }
        case ASTNodeType.AssignmentExp:{
            let varName = node.value;
            if (!variables.has(varName)){
                throw new Error("unknown variable: " + varName);
            }
            if (node.children.length > 0) {
                let child = node.children[0];
                result = evaluate(child, indent + "\t");
            }
            variables.set(varName, result);
            break;
        }
        case ASTNodeType.IntDeclaration: {
            let varName = node.value;
            if (node.children.length > 0) {
                let child = node.children[0];
                result = evaluate(child, indent + "\t");
            }
            variables.set(varName, result);
            break;
        }
        default:
            break;
    }

    if (verbose) {
        console.log(indent + "Result: " + result);
    } else if (indent == ""){
        if (node.type == ASTNodeType.IntDeclaration || node.type == ASTNodeType.AssignmentExp) {
            console.log(node.value + ": " + result);
        } else if (node.type != ASTNodeType.Program) {
            console.log(result);
        }
    }
    return result;
}
