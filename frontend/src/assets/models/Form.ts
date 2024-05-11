export interface IFormInput {
    name: string;
    type: string;
    placeholder: string;
    warning: string;
}

class FormInput implements IFormInput {
    name: string;
    type: string;
    placeholder: string;
    warning: string;

    constructor(name: string, type: string, placeholder: string, warning: string) {
        this.name = name;
        this.type = type;
        this.placeholder = placeholder;
        this.warning = warning;
    }
}

export default FormInput;