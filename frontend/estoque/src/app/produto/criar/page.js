'use client'
import {
    Box, FormControl, Paper, Typography, Snackbar, Button,
    TextField, InputLabel, OutlinedInput, InputAdornment, Autocomplete, Alert
} from "@mui/material";
import { useRouter } from "next/navigation";
import { createFilterOptions } from '@mui/material/Autocomplete';
import { useEffect, useState } from "react";

export default function CreateProduto() {
    const router = useRouter();
    const [categorias, setCategorias] = useState([]);
    const [fornecedores, setFornecedores] = useState([]);
    const QTD_MAX_PRODUTO = 100;

    const [categoriaSelecionada, setCategoriaSelecionada] = useState('');
    const [fornecedorSelecionado, setFornecedorSelecionado] = useState('')

    const [loading, setLoading] = useState(true);

    const [nome, setNome] = useState('');
    const [preco, setPreco] = useState('');
    const [quantidade, setQuantidade] = useState(0);

    const [alertOpen, setAlertOpen] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertSeverity, setAlertSeverity] = useState("success");

    const [objErrors, setObjError] = useState({
        nome: false,
        preco: false,
        quantidade: false,
        categoria: false,
        fornecedor: false
    })

    const validarCampos = () => {
        //obj
        const erros = {
            nome: nome === '',
            preco: preco === 0 || preco === '',
            quantidade: quantidade === 0 || quantidade === '' || quantidade > QTD_MAX_PRODUTO,
            categoria: !categoriaSelecionada,
            fornecedor: !fornecedorSelecionado
        };

        setObjError(erros);

        return !Object.values(erros).includes(true)
    }


    useEffect(() => {
        setLoading(false);

        const fetchCategorias = async () => {
            try {
                const response = await fetch('http://localhost:8080/categoria/all', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });
                const listaCategorias = await response.json();
                console.log(listaCategorias);
                setCategorias(listaCategorias);
            }
            catch (e) {
                console.log(e);
            }
        }

        const fetchFornecedores = async () => {
            try {
                const response = await fetch('http://localhost:8080/fornecedor/all', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: "include"
                });
                const listaFornecedor = await response.json();
                console.log(listaFornecedor);
                setFornecedores(listaFornecedor);
            }
            catch (e) {
                console.log(e);
            }
        }

        fetchFornecedores();
        fetchCategorias();
    }, []);

    const filterOptions = createFilterOptions({
        matchFrom: "start"
    });

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validarCampos()) {
            setAlertMessage('Erro ao salvar o produto!');
            setAlertOpen(true);
            setAlertSeverity("error")
            return;
        }

        const data = {
            nome: nome,
            preco: preco,
            quantidade: quantidade,
            categoria: {
                id: categoriaSelecionada.id,
            },
            fornecedor: {
                id: fornecedorSelecionado.id
            }
        }

        console.log(data);

        try {
            const response = await fetch('http://localhost:8080/produto/criar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
                credentials: "include"
            });

            if (!response.ok) {
                throw new Error("Erro ao criar produto");
            }

            const result = await response.json();
            console.log('Produto criado: ', result);

            setAlertMessage('Sucesso! Produto salvo no banco de dados!');
            setAlertOpen(true);
            setAlertSeverity("success");
        }
        catch (e) {
            console.log(e);
        }

    }

    const handleClose = () => {
        setAlertOpen(false);
    };

    if (loading) return <p>Carregando...</p>;

    return (
        <div>
            <Snackbar open={alertOpen} autoHideDuration={4000} onClose={handleClose}>
                <Alert onClose={handleClose} severity={alertSeverity} sx={{ width: '100%' }}>
                    {alertMessage}
                </Alert>
            </Snackbar>
            <Box display="flex"
                flexDirection={"column"}
                justifyContent="center"
                alignItems="center"
                minHeight="100vh"
                flexWrap={"wrap"}
                bgcolor="#f5f5f5">

                <Paper elevation={3} sx={{ p: 5, borderRadius: 4, maxWidth: 650, width: '100%' }}>
                    <Typography variant="h4" gutterBottom align="center" marginBottom={"20px"} className="font-mono">
                        Cadastrar Produto
                    </Typography>

                    <FormControl sx={{ m: 1 }}>
                        <TextField
                            id="nome"
                            label="Nome"
                            variant="outlined"
                            size="small"
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                            helperText={objErrors.nome && "Preencha o nome do produto."}
                            error={objErrors.nome}
                            required
                        />
                    </FormControl>
                    <FormControl sx={{ m: 1 }}>
                        <TextField
                            id="quantidade"
                            label="Quantidade"
                            variant="outlined"
                            type="number"
                            size="small"
                            value={quantidade}
                            error={objErrors.quantidade}
                            helperText={objErrors.quantidade && "Digite uma quantidade válida do produto."}
                            onChange={(e) => setQuantidade(e.target.value)}
                            required
                        />
                    </FormControl>
                    <FormControl sx={{ m: 1 }} className="w-md">
                        <InputLabel htmlFor="preco">Preço</InputLabel>
                        <OutlinedInput
                            id="preco"
                            startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                            label="Preço"
                            type="number"
                            size="small"
                            value={preco}
                            error={objErrors.preco}
                            onChange={(e) => setPreco(e.target.value)}
                            required
                        />
                    </FormControl>
                    <FormControl sx={{ m: 1 }}>
                        <Autocomplete
                            disablePortal
                            renderInput={(params) => <TextField error={objErrors.categoria} {...params} label="Categoria" />}
                            options={categorias}
                            sx={{ width: 300 }}
                            label="Categoria"
                            size="small"
                            onChange={(event, value) => setCategoriaSelecionada(value)}
                            getOptionLabel={(option) => (option.nome)}
                            filterOptions={filterOptions}
                        />
                    </FormControl>
                    <FormControl sx={{ m: 1 }}>
                        <Autocomplete
                            disablePortal
                            renderInput={(params) => <TextField error={objErrors.fornecedor} {...params} label="Fornecedor" />}
                            options={fornecedores}
                            sx={{ width: 300 }}
                            label="Fornecedor"
                            size="small"
                            onChange={(event, value) => setFornecedorSelecionado(value)}
                            getOptionLabel={(option) => (option.nome)}
                            filterOptions={filterOptions}
                        />
                    </FormControl>
                    <br></br>
                    <FormControl className="order-last" sx={{ m: 1 }}>
                        <button 
                        onClick={handleSubmit}
                        className="bg-blue-500 cursor-pointer rounded-md p-1 w-24 transition delay-150 duration-300 ease-in-out hover:-translate-y-1 hover:scale-110 hover:bg-indigo-800 text-white">
                            Enviar
                        </button>
                    </FormControl>
                </Paper>
            </Box>
        </div>
    )
}
