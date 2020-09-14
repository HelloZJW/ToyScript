import {TokenType} from "./TokenType";
import {Token} from "./Token";
import {isAlpha, isDigital} from "./Utils";

enum DfaState {
    Initial,
    Identifier,
    GT,
    GE,
    IntLiteral
}


class SimpleToken implements Token {
    text = '';
    type :TokenType;
}

export class SimpleLexer {
    private tokenText = '';
    private token = new SimpleToken();
    private tokens: SimpleToken[] = [];

    constructor() {}

    public tokenize(script :string) {
        let state = DfaState.Initial;
        for (let ch of script) {
            switch (state) {
                case DfaState.Initial:
                case DfaState.GE:
                    // 刚开始解析或者解析完成，是下一个 token 的开始
                    state = this.initToken(ch);
                    break;

                case DfaState.Identifier:
                    // 处于解析标识符
                    if (isDigital(ch) || isAlpha(ch)) {
                        // 如果是数字或者字母，留在当前状态继续解析下一个字符
                        this.tokenText += ch;
                    } else {
                        state = this.initToken(ch);
                    }
                    break;
                case DfaState.GT:
                    // 大于号
                    if (ch === '='){
                        this.tokenText += ch;
                        this.token.type = TokenType.GE;
                        state = DfaState.GE;
                    }else {
                        state = this.initToken(ch);
                    }
                    break;
                case DfaState.IntLiteral:
                    // 处于解析整数字面量
                    if (isDigital(ch)) {
                        // 如果是数字，留在当前状态继续解析下一个字符
                        this.tokenText += ch;
                    } else {
                        state = this.initToken(ch);
                    }
                    break;
                default:
                    break;
            }
        }
        // 把最后一个token送进去
        if (this.tokenText.length > 0) {
            this.initToken(script[script.length-1]);
        }

        return this.tokens.concat([]);
    }


    private initToken(ch) {
        if (this.tokenText.length > 0) {
            this.token.text = this.tokenText;
            this.tokens.push(this.token);

            this.token = new SimpleToken();
            this.tokenText = '';
        }
        let newState = DfaState.Initial;

        if (isAlpha(ch)) {
            // 第一个是字母，进入标识符状态
            newState = DfaState.Identifier;
            this.token.type = TokenType.Identifier;
            this.tokenText += ch;
        } else if (isDigital(ch)) {
            // 第一个是数字, 进入整型字面量状态
            newState = DfaState.IntLiteral;
            this.token.type = TokenType.IntLiteral;
            this.tokenText += ch;
        } else if (ch === '>') {
            // 第一个是大于号
            newState = DfaState.GT;
            this.token.type = TokenType.GT;
            this.tokenText += ch;
        }

        return newState;
    }
}