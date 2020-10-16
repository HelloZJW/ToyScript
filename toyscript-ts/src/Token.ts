import {TokenType} from "./TokenType";

export interface Token {
    text :string;
    type :TokenType;
}

export class SimpleToken implements Token {
    text = '';
    type: TokenType;
}
