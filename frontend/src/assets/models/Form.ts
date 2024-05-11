export interface IFormInput {
    name: string;
    type: string;
    placeholder: string;
    warning: string;
}

export interface IFilters {
    publicationType?: string;
    title?: string;
    cookUsername?: string;
    averageRating?: number;
    creationDate?: string;
    difficulty?: string[];
    category?: string[];
    ingredientName?: string;
    dietTypes?: string[];
    cookingTime?: number;
    prepTime?: number;
    cookTime?: number;
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