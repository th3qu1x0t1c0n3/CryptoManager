import React, {FormEvent, useEffect, useState} from "react";
import {IsignUp} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import FormInput from "../../assets/models/Form";
import {PortfolioService} from "../../services/PortfolioService";
import {PortfolioServerInstance} from "../../App";

interface ISignUpProps {
    setUser: (user: any) => void;
}

function SignUp({setUser}: ISignUpProps) {
    const portfolioService = new PortfolioService();
    const navigate = useNavigate();

    const [creationForm, setCreationForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: ''
    });
    const [createFormInfo, setCreateFromInfo] = useState([
        new FormInput('firstName', 'text', 'First Name', ''),
        new FormInput('lastName', 'text', 'Last Name', ''),
        new FormInput('email', 'text', 'Email', ''),
        new FormInput('password', 'password', 'Password', ''),
        new FormInput('confirmPassword', 'password', 'Confirm Password', '')
    ])

    function handleCreationChange(e: any) {
        setCreateFromInfo(createFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setCreationForm({...creationForm, [e.target.id]: e.target.value});
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();



        const signUpUser: IsignUp = {
            email: creationForm.email.trim(),
            firstName: creationForm.firstName.trim(),
            lastName: creationForm.lastName.trim(),
            password: creationForm.password.trim(),
        }

        portfolioService.signUp(signUpUser).then((response) => {
            setUser(response);
            sessionStorage.setItem('token', response.token);
            PortfolioServerInstance.defaults.headers.common['Authorization'] = `Bearer ${response.token}`;
            toast.success("Signed In Successfully!");
            navigate('/u/landing'); // TODO: Redirect to the landing page
        }).catch((error) => {
            toast.error(error.response?.data.message);
            console.log(error)
        });
    };

    return (
        <form onSubmit={handleSubmit} className="h-screen text-center">
            <div className="flex flex-col justify-center items-center mb-3">
                {
                    createFormInfo.map((formInfo, index) => (
                        <div key={index} className="my-2 xl:w-1/3 lg:w-1/2 md:w-3/4 w-11/12" id={formInfo.name}>
                            <input
                                className={`${formInfo.warning !== '' ? "border-cook-red" : "border-cook-light"} form-input border rounded-md p-2 w-full text-port-one`}
                                id={formInfo.name}
                                onChange={handleCreationChange} type={formInfo.type}
                                placeholder={formInfo.placeholder}/>
                        </div>
                    ))
                }
            </div>

            <button type="submit"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-5">
                Sign Up
            </button>
        </form>
    );
}

export default SignUp;