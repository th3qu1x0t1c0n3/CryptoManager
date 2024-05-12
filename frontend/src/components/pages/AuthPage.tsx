import React, {FormEvent, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";
import {toast} from "react-toastify";
import {PortfolioService} from "../../services/PortfolioService";
import {useNavigate} from "react-router-dom";
import {IsignIn, IsignUp, IUser} from "../../assets/models/Authentication";
import {PortfolioServerInstance} from "../../App";
import FormInput from "../../assets/models/Form";

interface IAuthPageProps {
    setUser: (user: any) => void;
}

function AuthPage({setUser}: IAuthPageProps) {
    const [isSignIn, setIsSignIn] = useState<boolean>(true);

    const portfolioService = new PortfolioService();
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    function onSucess(response: IUser) {
        setUser(response);
        sessionStorage.setItem('token', response.token);
        PortfolioServerInstance.defaults.headers.common['Authorization'] = response.token;
        toast.success("Signed In Successfully!");
        navigate('/u/transactions');
    }

    async function handleSubmitSignIn(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const user: IsignIn = {
            email: email,
            password: password
        };
        await portfolioService.signIn(user)
            .then((response) => {
                onSucess(response);
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }

    function toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    const [creationForm, setCreationForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: ''
    });
    const [createFormInfo, setCreateFromInfo] = useState([
        new FormInput('firstName', 'text', 'pages.auth.firstName', ''),
        new FormInput('lastName', 'text', 'pages.auth.lastName', ''),
        new FormInput('email', 'text', 'pages.auth.email', ''),
        new FormInput('password', 'password', 'pages.auth.password', ''),
        new FormInput('confirmPassword', 'password', 'pages.auth.confirmPassword', '')
    ])
    function handleCreationChange(e: any) {
        setCreateFromInfo(createFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setCreationForm({...creationForm, [e.target.id]: e.target.value});
    }
    const handleSubmitSignUp = (e: FormEvent) => {
        e.preventDefault();

        const signUpUser: IsignUp = {
            email: creationForm.email.trim(),
            firstName: creationForm.firstName.trim(),
            lastName: creationForm.lastName.trim(),
            password: creationForm.password.trim(),
        }

        portfolioService.signUp(signUpUser).then((response) => {
            onSucess(response);
        }).catch((error) => {
            toast.error(error.response?.data.message);
            console.log(error)
        });
    };

    return (
        <div>
            <div>
                {
                    isSignIn ? (
                        <form autoComplete="false" onSubmit={handleSubmitSignIn} className="text-center">
                            <div className="flex flex-col justify-center items-center mb-3">
                                <div className="mb-3 lg:w-1/2 md:w-1/2 w-11/12" id="formBasicEmail">
                                    <label className="text-lg font-bold text-right my-auto w-full">Email</label>
                                    <input
                                        type="email"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        className="form-input border border-gray-300 rounded-md p-2 w-full text-port-dark"
                                        placeholder={"Username"}
                                    />
                                </div>
                                <div className="mb-3 lg:w-1/3 md:w-1/2 w-11/12 relative" id="formBasicPassword">
                                    <label className="text-lg font-bold text-right my-auto w-full">Password</label>
                                    <input
                                        type={showPassword ? "text" : "password"}
                                        autoComplete={"current-password"}
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        className="form-input border border-gray-300 rounded-md p-2 w-full text-port-dark"
                                        placeholder={"Password"}
                                    />
                                    <button type="button" onClick={toggleShowPassword}
                                            className="absolute right-3 mt-3 top-1/2 transform -translate-y-1/2">
                                        <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}/>
                                    </button>
                                </div>
                            </div>
                            <button type="submit"
                                    className="border rounded transition ease-in duration-200 m-5 p-2 ">
                                Sign In
                            </button>
                            {/*<button type="button" onClick={() => {*/}
                            {/*    toast.info("Not implemented yet!")*/}
                            {/*}}*/}
                            {/*        className="border rounded transition ease-in duration-200 mb-5 p-2">*/}
                            {/*    Forgot Password?*/}
                            {/*</button>*/}
                            {/*<button type="button"*/}
                            {/*        className="border rounded transition ease-in duration-200 mx-5 p-2"*/}
                            {/*        onClick={() => {*/}
                            {/*            navigate("/authentication/signup")*/}
                            {/*        }}>*/}
                            {/*    Create Account*/}
                            {/*</button>*/}
                        </form>
                    ) : (
                        <></>
                        // <form onSubmit={handleSubmitSignUp} className="h-screen">
                        //     <div className="flex flex-col justify-center items-center mb-3">
                        //         {
                        //             createFormInfo.map((formInfo, index) => (
                        //                 <div key={index} className="my-2 xl:w-1/3 lg:w-1/2 md:w-3/4 w-11/12"
                        //                      id={formInfo.name}>
                        //                     <input
                        //                         className={`${formInfo.warning !== '' ? "border-cook-red" : "border-cook-light"} form-input border rounded-md p-2 w-full`}
                        //                         id={formInfo.name}
                        //                         onChange={handleCreationChange} type={formInfo.type}
                        //                         placeholder={formInfo.placeholder}/>
                        //                 </div>
                        //             ))
                        //         }
                        //     </div>
                        //
                        //     <button type="submit"
                        //             className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-5">
                        //         Sign Up
                        //     </button>
                        // </form>
                    )
                }
            </div>
        </div>
    );
}

export default AuthPage;