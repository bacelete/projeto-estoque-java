'use client'
import Alert from '@mui/material/Alert';
import { Snackbar, TextField } from "@mui/material";
import { useState } from 'react';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import InputLabel from '@mui/material/InputLabel';


export default function Registrar() {
    const [password, setPassword] = useState('');
    const [passwordConfirmation, setPasswordConfirmation] = useState('');

    const [validLength, setValidLength] = useState(false);
    const [hasUpper, setHasUpper] = useState(false);
    const [hasNumber, setHasNumber] = useState(false);
    const [isPresent, setIsPresent] = useState(false);

    const [alertOpen, setAlertOpen] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertSeverity, setAlertSeverity] = useState("success");

    const [username, setUsername] = useState('');
    const [userNameError, setUsernameError] = useState({ username: false });

    const [role, setRole] = useState('USER');

    const handleRole = (event) => {
        setRole(event.target.value);
    };

    const checkValidity = (data) => {
        setValidLength(data.length >= 8);
        setHasUpper(/[A-Z]/.test(data));
        setHasNumber(/\d/.test(data));
        setIsPresent(data !== '');
    }

    const validarCampos = () => {
        const erro = {
            username: username.trim() === ''
        }

        setUsernameError(erro);

        if (!hasNumber || !hasUpper || userNameError.username || !validLength || (password !== passwordConfirmation) || !isPresent) {
            return false;
        }

        return true;
    }


    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validarCampos()) {
            setAlertMessage('Erro ao criar conta! Preencha os campos corretamente');
            setAlertOpen(true);
            setAlertSeverity("error")
            return;
        }

        try {
            const data = {
                login: username,
                password: password,
                role: role
            }

            console.log('data: ' + data);

            const response = await fetch(`http://localhost:8080/auth/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ data }),
            });

            if (!response.ok) {
                setAlertMessage("Erro ao criar a conta!");
                setAlertOpen(true);
                setAlertSeverity("error");
            }

            setAlertMessage('Sucesso! Conta criada com sucesso.');
            setAlertOpen(true);
            setAlertSeverity("success")

        }
        catch (e) {
            console.log(e);
        }

    }

    const handleClose = () => {
        setAlertOpen(false);
    };

    return (
        <>
            <div>
                <Snackbar open={alertOpen} autoHideDuration={4000} onClose={handleClose}>
                    <Alert onClose={handleClose} severity={alertSeverity} sx={{ width: '100%' }}>
                        {alertMessage}
                    </Alert>
                </Snackbar>
                <form
                    className="text-black flex flex-col gap-4 max-w-sm mx-auto my-10 p-9 bg-white rounded-md shadow-2xl h-[40rem]"
                    method="POST">
                    <span className="text-3xl text-center font-bold">Criar conta</span>
                    <div className="grid grid-cols w-full gap-4 my-2 p-4 mx-auto">
                        <TextField
                            id="login"
                            label="Usuário"
                            variant="standard"
                            size="small"
                            onBlur={(e) => {
                                setUsername(e.target.value)
                                setUsernameError({ username: e.target.value.trim() === '' })
                            }}
                            error={userNameError.username}
                            helperText={userNameError.username && "O nome de usuário é obrigatório"}
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
                            }}
                            error={!isPresent}
                            helperText={!isPresent && "Por favor, digite uma senha"}
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
                    <FormControl sx={{ minWidth: 120 }} size='small'>
                        <InputLabel id="demo-simple-select-helper-label">Perfil</InputLabel>
                        <Select
                            labelId="demo-simple-select-filled-label"
                            id="demo-simple-select-filled"
                            onChange={handleRole}
                            value={role}
                            label="Perfil"
                        >
                            <MenuItem value={'ADMIN'}>ADMIN</MenuItem>
                            <MenuItem value={'USER'}>USER</MenuItem>
                        </Select>
                        <FormHelperText>Selecione um perfil de usuário.</FormHelperText>
                    </FormControl>
                    <button
                        onClick={handleSubmit}
                        className="bg-blue-500 mt-6 cursor-pointer rounded-md p-1 transition delay-150 duration-300 ease-in-out hover:-translate-y-1 hover:scale-110 hover:bg-indigo-800 text-white">
                        Registrar
                    </button>
                </form>
            </div>
        </>
    )
}