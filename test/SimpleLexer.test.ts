import {SimpleLexer} from "../src/SimpleLexer";

test('解析 age >= 45', () => {
    let expected = JSON.stringify([{"text": "age", "type": "Identifier"}, {"text": ">=", "type": "GE"}, {
        "text": "45",
        "type": "IntLiteral"
    }]);
    expect(JSON.stringify(new SimpleLexer().tokenize('age >= 45'))).toBe(expected);
});