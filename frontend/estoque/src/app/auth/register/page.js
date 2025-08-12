'use client'
import Alert from '@mui/material/Alert';
import { Snackbar, TextField, InputAdornment } from "@mui/material";

export default function Registrar() {
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
                            required
                        />
                        <TextField
                            id="password-confirmation"
                            label="Confirme a senha"
                            variant="standard"
                            size="small"
                            type="password"
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