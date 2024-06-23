import React, {useState} from "react";
import SignIn from "../auth/SignIn";
import SignUp from "../auth/SignUp";

interface IAuthPageProps {
    setUser: (user: any) => void;
}

function AuthPage({setUser}: IAuthPageProps) {
    const [tab, setTab] = useState('signin');
    const tabs = [
        {id: 'signin', label: 'signin'},
        {id: 'signup', label: 'signup'},
    ];

    return (
        <div>
            <div className="items-center my-2 mx-auto text-center w-full">
                {tabs.map(tabItem => (
                    <button
                        key={tabItem.id}
                        className={`py-2 px-4 rounded-lg border border-port-four ${tab === tabItem.id ? 'bg-port-one' : 'bg-port-two hover:bg-blue-600'}`}
                        onClick={() => {
                            setTab(tabItem.id)
                        }}
                        style={{position: 'relative'}}
                    >
                        {tabItem.label}
                    </button>
                ))}
            </div>

            {tab === 'signin' && <SignIn setUser={setUser}/>}
            {tab === 'signup' && <SignUp setUser={setUser}/>}
        </div>
    );
}

export default AuthPage;