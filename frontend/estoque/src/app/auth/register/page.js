'use client'
import Alert from '@mui/material/Alert';
import { Snackbar, TextField, InputAdornment } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { useState } from 'react';

export default function Registrar() {
    const [password, setPassword] = useState('');
    const [passwordConfirmation, setPasswordConfirmation] = useState('');

    const [validLength, setValidLength] = useState(false);
    const [hasUpper, setHasUpper] = useState(false);
    const [hasNumber, setHasNumber] = useState(false);

    const checkValidity = (data) => {
        setValidLength(data.length >= 8);
        setHasUpper(/[A-Z]/.test(data));
        setHasNumber(/\d/.test(data));
    }

    return (
        <>
            <div>
                <form
                    className="text-black flex flex-col gap-4 max-w-sm mx-auto my-35 p-9 bg-white rounded-md shadow-2xl h-[30rem]"
                    method="POST">
                    <span className="text-3xl text-center font-bold">Criar conta</span>
                    <div className="grid grid-cols w-full gap-4 my-2 p-4 mx-auto">
                        <TextField
                            id="login"
                            label="Usuário"
                            variant="standard"
                            size="small"
                        />
                        <TextField
                            id="password"
                            label="Senha"
                            variant="standard"
                            size="small"
                            type="password"
                            onChange={(e) => {
                                checkValidity(e.target.value)
                                setPassword(e.target.value)
                            }
                            }
                            required
                        />
                        <ul className='text-sm p-2'>
                            <li className={validLength ? 'text-green-500' : 'text-rose-700'}>No mínimo 8 caracteres.</li>
                            <li className={hasUpper ? 'text-green-500' : 'text-rose-700'}>No mínimo 1 letra maiûscula</li>
                            <li className={hasNumber ? 'text-green-500' : 'text-rose-700'}>No mínimo 1 número</li>
                        </ul>
                        <TextField
                            id="password-confirmation"
                            label="Confirme a senha"
                            variant="standard"
                            size="small"
                            type="password"
                            onChange={(e) => setPasswordConfirmation(e.target.value)}
                            error={(password !== passwordConfirmation)}
                            helperText={(password !== passwordConfirmation) && "As senhas devem ser iguais"}
                            required
                        />
                    </div>
                    <button className="bg-blue-500 cursor-pointer rounded-md p-1 transition delay-150 duration-300 ease-in-out hover:-translate-y-1 hover:scale-110 hover:bg-indigo-800 text-white">
                        Registrar
                    </button>
                </form>
            </div>
        </>
    )
}