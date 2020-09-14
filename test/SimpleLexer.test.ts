import {SimpleLexer} from "../src/SimpleLexer";


// 关系表达式
let script = 'age >= 45';
test(script, () => {
    let expected = JSON.stringify([{"text": "age", "type": "Identifier"}, {"text": ">=", "type": "GE"}, {
        "text": "45",
        "type": "IntLiteral"
    }]);
    expect(JSON.stringify(new SimpleLexer().tokenize(script))).toBe(expected);
});

// 解析 变量声明和初始化语句
let script2 = 'int age = 40';
test(script2, () => {
    let expected = JSON.stringify([{"text": "int", "type": "Int"}, {"text": "age", "type": "Identifier"}, {
        "text": "=",
        "type": "="
    }, {"text": "40", "type": "IntLiteral"}]);
    expect(JSON.stringify(new SimpleLexer().tokenize(script2))).toBe(expected);
});


let script3 = 'intA age = 40';
test(script3, () => {
    let expected = JSON.stringify([{"text": "intA", "type": "Identifier"}, {
        "text": "age",
        "type": "Identifier"
    }, {"text": "=", "type": "="}, {"text": "40", "type": "IntLiteral"}]);
    expect(JSON.stringify(new SimpleLexer().tokenize(script3))).toBe(expected);
});
