import {TokenType} from "./TokenType";
import {Token} from "./Token";
import {isAlpha, isBlank, isDigital} from "./Utils";

enum DfaState {
    Initial,
    Identifier,
    GT,
    GE,
    IntLiteral,
    Assignment,

    Plus,
    Minus,
    Star,
    Slash,

    Int1,
    Int2,
    Int3,
    Int
}


class SimpleToken implements Token {
    text = '';
    type: TokenType;
}

export class SimpleLexer {
    private tokenText = '';
    private token = new SimpleToken();
    private tokens: SimpleToken[] = [];

    constructor() {
    }

    public tokenize(script: string) {
        let state = DfaState.Initial;
        for (let ch of script) {
            switch (state) {
                case DfaState.Initial:
                case DfaState.GE:
                case DfaState.Assignment:
                case DfaState.Plus:
                case DfaState.Minus:
                case DfaState.Star:
                case DfaState.Slash:
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
                    if (ch === '=') {
                        this.tokenText += ch;
                        this.token.type = TokenType.GE;
                        state = DfaState.GE;
                    } else {
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
                case DfaState.Int1:
                    // 大于号
                    if (ch === 'n') {
                        this.tokenText += ch;
                        state = DfaState.Int2;
                    } else if (isDigital(ch) || isAlpha(ch)) {
                        // 如果是数字或者字母，会进入标识符状态
                        state = DfaState.Identifier;
                        this.tokenText += ch;
                    } else {
                        state = this.initToken(ch);
                    }
                    break;
                case DfaState.Int2:
                    // 大于号
                    if (ch === 't') {
                        this.tokenText += ch;
                        state = DfaState.Int3;
                    } else if (isDigital(ch) || isAlpha(ch)) {
                        // 如果是数字或者字母，会进入标识符状态
                        state = DfaState.Identifier;
                        this.tokenText += ch;
                    } else {
                        state = this.initToken(ch);
                    }
                    break;
                case DfaState.Int3:
                    if (isBlank(ch)) {
                        this.token.type = TokenType.Int;
                        state = this.initToken(ch)
                    } else {
                        // 进入标识符状态
                        state = DfaState.Identifier;
                        this.tokenText += ch;
                    }
                    break;
                default:
                    break;
            }
        }
        // 把最后一个token送进去
        if (this.tokenText.length > 0) {
            this.initToken(script[script.length - 1]);
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
            if (ch === 'i') {
                // 第一个是 i，可能是 int 关键字， 也可能是标识符
                newState = DfaState.Int1;
                this.token.type = TokenType.Identifier;
                this.tokenText += ch;
            } else {
                newState = DfaState.Identifier;
                this.token.type = TokenType.Identifier;
                this.tokenText += ch;
            }
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
        } else if (ch === '=') {
            // 第一个是 等于
            newState = DfaState.Assignment;
            this.token.type = TokenType.Assignment;
            this.tokenText += ch;
        } else if (ch === '+') {
            // 第一个是 等于
            newState = DfaState.Plus;
            this.token.type = TokenType.Plus;
            this.tokenText += ch;
        }else if (ch === '-') {
            // 第一个是 等于
            newState = DfaState.Minus;
            this.token.type = TokenType.Minus;
            this.tokenText += ch;
        }else if (ch === '*') {
            // 第一个是 等于
            newState = DfaState.Star;
            this.token.type = TokenType.Star;
            this.tokenText += ch;
        }else if (ch === '/') {
            // 第一个是 等于
            newState = DfaState.Slash;
            this.token.type = TokenType.Slash;
            this.tokenText += ch;
        }

        return newState;
    }
}

// console.log(JSON.stringify(new SimpleLexer().tokenize('2+3*5')) );