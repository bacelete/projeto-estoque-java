'use client'
import { useEffect, useState } from "react"
import Alert from '@mui/material/Alert';
import { Snackbar, TextField, InputAdornment } from "@mui/material";
import { useRouter } from 'next/navigation'
import AccountCircle from '@mui/icons-material/AccountCircle';
import LockIcon from '@mui/icons-material/Lock';

export default function Login() {
    const router = useRouter();

    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertSeverity, setAlertSeverity] = useState("success");


    const handleSubmit = async (e) => {
        e.preventDefault() // impede o envio padrão do form


        const data = {
            login: login,
            password: password
        };

        console.log("data: ", data);

        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ login, password }),
                credentials: "include"
            })


            if (!response.ok) {
                let errorMessage = 'Erro ao fazer login';
                try {
                    const errorData = await response.json();
                    errorMessage = errorData.message || errorMessage;
                } catch (e) {
                    //...
                }

                setAlertMessage('Usuário e/ou senha inválido(s).');
                setAlertOpen(true);
                setAlertSeverity("error");
                throw new Error(errorMessage);
            }

            setAlertMessage('Sucesso! Autenticação com sucesso!');
            setAlertOpen(true);
            setAlertSeverity("success");
            setLogin('');
            setPassword('');

            setTimeout(() => {
                router.push('/produto/criar');  //redirecionamento
            }, 1500);

        } catch (err) {
            console.log(err);
        }
    }

    const handleClose = () => {
        setAlertOpen(false);
    };

    const handleRegister = () => {
        router.push('/auth/register'); 
    }

    return <>
        <div>
            <Snackbar open={alertOpen} autoHideDuration={4000} onClose={handleClose}>
                <Alert onClose={handleClose} severity={alertSeverity} sx={{ width: '100%' }}>
                    {alertMessage}
                </Alert>
            </Snackbar>
            <form
                onSubmit={handleSubmit}
                className="text-black flex flex-col gap-4 max-w-sm mx-auto my-35 p-9 bg-white rounded-md shadow-2xl h-[30rem]"
                method="POST">
                <span className="text-3xl text-center font-bold">Login</span>
                <div className="grid grid-cols w-full gap-4 my-2 p-4 mx-auto">
                    <TextField
                        id="login"
                        label="Usuário"
                        variant="standard"
                        size="small"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                        slotProps={{
                            input: {
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <AccountCircle fontSize="small" />
                                    </InputAdornment>
                                ),
                            },
                        }}
                        required
                    />
                    <TextField
                        id="password"
                        label="Senha"
                        variant="standard"
                        size="small"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        slotProps={{
                            input: {
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <LockIcon fontSize="small" />
                                    </InputAdornment>
                                ),
                            },
                        }}
                        type="password"
                        required
                    />
                </div>
                <button className="bg-blue-500 cursor-pointer rounded-md p-1 transition delay-150 duration-300 ease-in-out hover:-translate-y-1 hover:scale-110 hover:bg-indigo-800 text-white">
                    Entrar
                </button>
                <span className="mx-auto mt-3 text-neutral-500">Não possui uma conta? Registre-se.</span>
                <button 
                onClick={handleRegister}
                className="bg-blue-500 cursor-pointer mx-auto rounded-md p-1 w-36 transition delay-150 duration-300 ease-in-out hover:-translate-y-1 hover:scale-110 hover:bg-indigo-800 text-white">
                    Registrar
                </button>
            </form>
        </div>
    </>
}