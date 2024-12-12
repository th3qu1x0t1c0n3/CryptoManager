import React, {useState} from "react";
import {IsignIn, IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {PortfolioServerInstance} from "../../App";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {PortfolioService} from "../../services/PortfolioService";


interface SignInProps {
    setUser: (user: IUser) => void;
}

function SignIn({setUser}: SignInProps) {
    const cookbookService = new PortfolioService();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const user: IsignIn = {
            email: username,
            password: password
        };
        await cookbookService.signIn(user)
            .then((response) => {
                setUser(response);
                sessionStorage.setItem('token', response.token);
                PortfolioServerInstance.defaults.headers.common['Authorization'] = `Bearer ${response.token}`;
                toast.success("Signed In Successfully!");
                navigate('/u/landing'); // TODO: Redirect to the landing page
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }

    function toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    return (
        <form autoComplete="false" onSubmit={handleSubmit} className="text-center">
            <div className="flex flex-col justify-center items-center mb-3">
                <div className="mb-3 lg:w-1/2 md:w-1/2 w-11/12" id="formBasicEmail">
                    <label className="text-lg font-bold text-right my-auto w-full">Email</label>
                    <input
                        type="email"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="form-input border border-gray-300 rounded-md p-2 w-full"
                        placeholder={"username"}
                    />
                </div>
                <div className="mb-3 lg:w-1/3 md:w-1/2 w-11/12 relative" id="formBasicPassword">
                    <label className="text-lg font-bold text-right my-auto w-full">Password</label>
                    <input
                        type={showPassword ? "text" : "password"}
                        autoComplete={"current-password"}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="form-input border border-gray-300 rounded-md p-2 w-full"
                        placeholder={"Password"}
                    />
                    <button type="button" onClick={toggleShowPassword}
                            className="absolute right-3 mt-3 top-1/2 transform -translate-y-1/2">
                        <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}/>
                    </button>
                </div>
            </div>
            <button type="submit"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 m-5 p-2">
                Sign In
            </button>
            <button type="button" onClick={() => {
                toast.info("Not implemented yet!")
            }}
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 mb-5 p-2">
                Forgot Password?
            </button>
        </form>
    );
}

export default SignIn;
