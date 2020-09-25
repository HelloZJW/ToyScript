import {Token} from "./Token";

export class TokenReader {
    protected tokens: Token[] = [];
    public position = 0;

    constructor(tokens) {
        this.tokens = tokens;
    }

    /**
     * 从 Token 流中取出下一个 Token，如果流为空，返回 null
     */
    public read(): Token {
        if (this.position < this.tokens.length) {
            return this.tokens[this.position++];
        }
        return null;
    }

    /**
     * Token流回退一步。
     */
    public unread() {
        if (this.position > 0)
            this.position--;
    }

    /**
     *
     */
    public peek(): Token {
        if (this.position < this.tokens.length) {
            return this.tokens[this.position];
        }
        return null;
    }
}