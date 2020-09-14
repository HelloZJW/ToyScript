import {TokenType} from "./TokenType";

export interface Token {

    getType: () => TokenType;
    getText: () => string;

}