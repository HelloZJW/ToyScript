export  function isDigital(ch: string) {
    return ch >= '0' && ch <= '9';
}

export  function isAlpha(ch: string) {
    return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
}