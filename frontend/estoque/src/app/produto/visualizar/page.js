'use client'
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Box } from '@mui/material';
import { useEffect, useState } from 'react';
import Typography from '@mui/material/Typography';

export default function VisualizarProdutos() {
    const [produtos, setProdutos] = useState([]);

    useEffect(() => {
        try {
            const fetchProdutos = async () => {
                const response = await fetch(`http://localhost:8080/produto/all`, {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error();
                }

                const produtos = await response.json();
                setProdutos(produtos);
                console.log('Produtos: ', produtos);
            };
            fetchProdutos();
        }
        catch (e) {
            console.log(e)
        }
    }, [])

    return (
        <>
            <Box sx={{ p: 5, m : 5}}>
                <Typography color="black" variant="h4" component={"h2"}>Ver Produtos</Typography>
                <TableContainer component={Paper} className='my-5'>
                    <Table sx={{ minWidth: 650, p: 3}} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Nome</TableCell>
                                <TableCell align="right">Preço</TableCell>
                                <TableCell align="right">Quantidade</TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {produtos.map((produto) => (
                                <TableRow
                                    key={produto.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {produto.nome}
                                    </TableCell>
                                    <TableCell align="right">{produto.preco}</TableCell>
                                    <TableCell align="right">{produto.quantidade}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
        </>
    )
}